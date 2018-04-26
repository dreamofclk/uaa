package com.shunneng.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shunneng.uaa.domain.OauthValidateCode;

import com.shunneng.uaa.repository.OauthValidateCodeRepository;
import com.shunneng.uaa.web.rest.errors.BadRequestAlertException;
import com.shunneng.uaa.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OauthValidateCode.
 */
@RestController
@RequestMapping("/api")
public class OauthValidateCodeResource {

    private final Logger log = LoggerFactory.getLogger(OauthValidateCodeResource.class);

    private static final String ENTITY_NAME = "oauthValidateCode";

    private final OauthValidateCodeRepository oauthValidateCodeRepository;

    public OauthValidateCodeResource(OauthValidateCodeRepository oauthValidateCodeRepository) {
        this.oauthValidateCodeRepository = oauthValidateCodeRepository;
    }

    /**
     * POST  /oauth-validate-codes : Create a new oauthValidateCode.
     *
     * @param oauthValidateCode the oauthValidateCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new oauthValidateCode, or with status 400 (Bad Request) if the oauthValidateCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/oauth-validate-codes")
    @Timed
    public ResponseEntity<OauthValidateCode> createOauthValidateCode(@Valid @RequestBody OauthValidateCode oauthValidateCode) throws URISyntaxException {
        log.debug("REST request to save OauthValidateCode : {}", oauthValidateCode);
        if (oauthValidateCode.getId() != null) {
            throw new BadRequestAlertException("A new oauthValidateCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OauthValidateCode result = oauthValidateCodeRepository.save(oauthValidateCode);
        return ResponseEntity.created(new URI("/api/oauth-validate-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oauth-validate-codes : Updates an existing oauthValidateCode.
     *
     * @param oauthValidateCode the oauthValidateCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated oauthValidateCode,
     * or with status 400 (Bad Request) if the oauthValidateCode is not valid,
     * or with status 500 (Internal Server Error) if the oauthValidateCode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/oauth-validate-codes")
    @Timed
    public ResponseEntity<OauthValidateCode> updateOauthValidateCode(@Valid @RequestBody OauthValidateCode oauthValidateCode) throws URISyntaxException {
        log.debug("REST request to update OauthValidateCode : {}", oauthValidateCode);
        if (oauthValidateCode.getId() == null) {
            return createOauthValidateCode(oauthValidateCode);
        }
        OauthValidateCode result = oauthValidateCodeRepository.save(oauthValidateCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, oauthValidateCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oauth-validate-codes : get all the oauthValidateCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of oauthValidateCodes in body
     */
    @GetMapping("/oauth-validate-codes")
    @Timed
    public List<OauthValidateCode> getAllOauthValidateCodes() {
        log.debug("REST request to get all OauthValidateCodes");
        return oauthValidateCodeRepository.findAll();
        }

    /**
     * GET  /oauth-validate-codes/:id : get the "id" oauthValidateCode.
     *
     * @param id the id of the oauthValidateCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the oauthValidateCode, or with status 404 (Not Found)
     */
    @GetMapping("/oauth-validate-codes/{id}")
    @Timed
    public ResponseEntity<OauthValidateCode> getOauthValidateCode(@PathVariable Long id) {
        log.debug("REST request to get OauthValidateCode : {}", id);
        OauthValidateCode oauthValidateCode = oauthValidateCodeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(oauthValidateCode));
    }

    /**
     * DELETE  /oauth-validate-codes/:id : delete the "id" oauthValidateCode.
     *
     * @param id the id of the oauthValidateCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/oauth-validate-codes/{id}")
    @Timed
    public ResponseEntity<Void> deleteOauthValidateCode(@PathVariable Long id) {
        log.debug("REST request to delete OauthValidateCode : {}", id);
        oauthValidateCodeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
