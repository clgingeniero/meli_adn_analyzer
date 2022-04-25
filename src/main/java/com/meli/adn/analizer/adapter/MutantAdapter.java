package com.meli.adn.analizer.adapter;

import com.meli.adn.analizer.adapter.dto.AdnDTO;
import com.meli.adn.analizer.adapter.interfaces.AbstractAwsDynamo;
import com.meli.adn.analizer.adapter.interfaces.IAdapter;
import com.meli.adn.analizer.adapter.model.Adn;
import com.meli.adn.analizer.commons.Request;
import com.meli.adn.analizer.commons.Response;
import com.meli.adn.analizer.commons.Status;
import com.meli.adn.analizer.controller.dto.MutantResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component("MutantAdapter")
public class MutantAdapter extends AbstractAwsDynamo implements IAdapter<Response<MutantResponseDTO>, Request<AdnDTO>> {

	public MutantAdapter(@Value("${meli.adn.table}") String adnTable) {
		super(adnTable);
	}

    @Override
    public Response<MutantResponseDTO> callService(Request<AdnDTO> request) {
        Adn adn = new Adn();
        adn.setDna(Arrays.toString(request.getDna().getAdnEval()));
        adn.setMutant(request.getDna().getIsMutant());
        return Response.<MutantResponseDTO>builder()
                .status(Objects.isNull(load(adn)) ? saveAdn(adn) : getStatus(adn))
                .data(MutantResponseDTO.builder().isMutant(request.getDna().getIsMutant()).build())
                .build();
    }

    private Status saveAdn(Adn adn) {
        try {
            save(adn);
        } catch (Exception e) {
           return Status.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        }
        return getStatus(adn);
    }

    private Status getStatus(Adn adn) {
        return Boolean.TRUE.equals(adn.getMutant()) ?
                Status.builder().code(HttpStatus.OK.value())
                        .description(HttpStatus.OK.getReasonPhrase()).build() :
                Status.builder().code(HttpStatus.FORBIDDEN.value())
                        .description(HttpStatus.FORBIDDEN.getReasonPhrase()).build();
    }
}
