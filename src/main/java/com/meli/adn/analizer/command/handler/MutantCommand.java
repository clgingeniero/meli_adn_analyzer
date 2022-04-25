package com.meli.adn.analizer.command.handler;

import com.meli.adn.analizer.adapter.dto.AdnDTO;
import com.meli.adn.analizer.adapter.interfaces.IAdapter;
import com.meli.adn.analizer.command.MutantReqCommand;
import com.meli.adn.analizer.commons.Request;
import com.meli.adn.analizer.commons.Response;
import com.meli.adn.analizer.commons.Status;
import com.meli.adn.analizer.controller.dto.MutantResponseDTO;
import com.meli.adn.analizer.engine.command.ICommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class MutantCommand implements ICommandHandler<MutantResponseDTO, MutantReqCommand> {

	@Autowired
    @Qualifier("MutantAdapter")
    private IAdapter<Response<MutantResponseDTO>, Request<AdnDTO>> mutantAdapter;

	public static final String MUTANT_REGEX = "^([A-Z]*)([A]{4}|[C]{4}|[T]{4}|[G]{4})([A-Z]*)$";
	public static final String STRUCTURE_REGEX = "^[ACTG]+$";

	@Override
	public Response<MutantResponseDTO> handle(MutantReqCommand command) {

		if (Objects.isNull(command.getRequest().getDna()) || !validateDna(command.getRequest().getDna())) {
			return badRequest();
		}

		return mutantAdapter.callService(Request.<AdnDTO>builder()
						.dna(AdnDTO.builder()
						.adnEval(command.getRequest().getDna())
						.isMutant(getTotalNitrogenous(command.getRequest().getDna()) > 1)
						.build()).build()
		);
	}

	public boolean validateDna(String[] dna) {
		long validate = Arrays.stream(dna)
				.map(b -> b.matches(STRUCTURE_REGEX))
				.filter(s -> s.equals(false))
				.count();
		return validate == 0;
	}

	private long getTotalNitrogenous(String[] dna) {
		String[] transpose = new String[dna.length];
		String diagonal = "";
		String diagonalInverse = "";

		for (int i = 0; i < dna.length; i++) {
			diagonal = diagonal.concat(String.valueOf(dna[i].charAt(i)));
			diagonalInverse = diagonalInverse.concat(String.valueOf(dna[i].charAt(5-i)));
			for (String s : dna) {
				transpose[i] = (transpose[i] == null)
						? String.valueOf(s.charAt(i))
						: transpose[i].concat(String.valueOf(s.charAt(i)));
			}
		}

		return isMutant(dna)
				+ isMutant(transpose)
				+ (isMatches(diagonal) ? 1 : 0)
				+ (isMatches(diagonalInverse) ? 1 : 0);
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

	private Response<MutantResponseDTO> badRequest() {
		return Response.<MutantResponseDTO>builder()
				.status(Status.builder().code(HttpStatus.BAD_REQUEST.value())
						.description(HttpStatus.BAD_REQUEST.getReasonPhrase()).build())
				.build();
	}

}

