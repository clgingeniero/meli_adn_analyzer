package com.meli.adn.analizer.adapter;

import com.meli.adn.analizer.adapter.dto.AdnDTO;
import com.meli.adn.analizer.adapter.interfaces.AbstractAwsDynamo;
import com.meli.adn.analizer.adapter.interfaces.IAdapter;
import com.meli.adn.analizer.adapter.model.Adn;
import com.meli.adn.analizer.commons.Request;
import com.meli.adn.analizer.commons.Response;
import com.meli.adn.analizer.commons.Status;
import com.meli.adn.analizer.commons.eums.CodeEnums;
import com.meli.adn.analizer.controller.dto.MutantResponseDTO;
import org.springframework.beans.factory.annotation.Value;
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
                .status((Objects.isNull(load(adn))) ? saveAdn(adn) : getStatus(adn))
                .data(MutantResponseDTO.builder().isMutant(request.getDna().getIsMutant()).build()).build();
    }

    private Status saveAdn(Adn adn) {
        try {
            save(adn);
            return getStatus(adn);
        } catch (Exception e) {
           return Status.builder().code(CodeEnums.SERVER_ERROR.getCode()).description(CodeEnums.
                   SERVER_ERROR.getCode()).build();
        }
    }

    private Status getStatus(Adn adn) {
        return adn.getMutant() ?
                Status.builder().code(CodeEnums.SUCCESS.getCode()).description(CodeEnums.
                    SUCCESS.getCode()).build() :
                Status.builder().code(CodeEnums.INVALID.getCode()).description(CodeEnums.
                        INVALID.getCode()).build();
    }
}
