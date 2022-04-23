package com.meli.adn.analizer.command.handler;

import com.meli.adn.analizer.adapter.dto.AdnDTO;
import com.meli.adn.analizer.adapter.interfaces.IAdapter;
import com.meli.adn.analizer.command.MutantReqCommand;
import com.meli.adn.analizer.commons.Request;
import com.meli.adn.analizer.commons.Response;
import com.meli.adn.analizer.controller.dto.MutantResponseDTO;
import com.meli.adn.analizer.engine.command.ICommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MutantCommand implements ICommandHandler<MutantResponseDTO, MutantReqCommand> {

	public static final String MUTANT_REGEX = "^([A-Z]*)([A]{4}|[C]{4}|[T]{4}|[G]{4})([A-Z]*)$";
	@Autowired
    @Qualifier("MutantAdapter")
    private IAdapter<Response<MutantResponseDTO>, Request<AdnDTO>> dynamoAdapter;

	@Override
	public Response<MutantResponseDTO> handle(MutantReqCommand command) {

		var dna = command.getRequest().getDna();
		String[] transpose = new String[dna.length];
		String diagonal = "";
		String reverseDiagonal = "";

		for (int i = 0; i < dna.length; i++) {
			diagonal = diagonal.concat(String.valueOf(dna[i].charAt(i)));
			reverseDiagonal = reverseDiagonal.concat(String.valueOf(dna[i].charAt(5-i)));
			for (String s : dna) {
				transpose[i] = (transpose[i] == null)
						? String.valueOf(s.charAt(i))
						: transpose[i].concat(String.valueOf(s.charAt(i)));
			}
		}

		return dynamoAdapter.callService(Request.<AdnDTO>builder()
						.dna(AdnDTO.builder()
						.adnEval(dna)
						.isMutant(getTotalMutants(command, transpose, diagonal, reverseDiagonal) > 1)
						.build()).build()
		);
	}

	private long getTotalMutants(MutantReqCommand command, String[] transpose, String diagonal, String diagonalEnd) {
		return isMutant(command.getRequest().getDna())
				+ isMutant(transpose)
				+ (isMatches(diagonal) ? 1 : 0)
				+ (isMatches(diagonalEnd) ? 1 : 0);
	}

	private Long isMutant(String[] dna) {
		return Arrays.stream(dna)
				.map(this::isMatches)
				.filter(s -> s.equals(true))
				.count();
	}

	private boolean isMatches(String str) {
		return str.matches(MUTANT_REGEX);
	}

}

