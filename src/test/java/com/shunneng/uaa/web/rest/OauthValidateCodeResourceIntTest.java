package com.shunneng.uaa.web.rest;

import com.shunneng.uaa.UaaApp;

import com.shunneng.uaa.config.SecurityBeanOverrideConfiguration;

import com.shunneng.uaa.domain.OauthValidateCode;
import com.shunneng.uaa.repository.OauthValidateCodeRepository;
import com.shunneng.uaa.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.shunneng.uaa.web.rest.TestUtil.sameInstant;
import static com.shunneng.uaa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OauthValidateCodeResource REST controller.
 *
 * @see OauthValidateCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaApp.class)
public class OauthValidateCodeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_PRINCIPAL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EXPIRE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CODE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE = "BBBBBBBBBB";

    @Autowired
    private OauthValidateCodeRepository oauthValidateCodeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOauthValidateCodeMockMvc;

    private OauthValidateCode oauthValidateCode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OauthValidateCodeResource oauthValidateCodeResource = new OauthValidateCodeResource(oauthValidateCodeRepository);
        this.restOauthValidateCodeMockMvc = MockMvcBuilders.standaloneSetup(oauthValidateCodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OauthValidateCode createEntity(EntityManager em) {
        OauthValidateCode oauthValidateCode = new OauthValidateCode()
            .code(DEFAULT_CODE)
            .principal(DEFAULT_PRINCIPAL)
            .expireTime(DEFAULT_EXPIRE_TIME)
            .codeType(DEFAULT_CODE_TYPE);
        return oauthValidateCode;
    }

    @Before
    public void initTest() {
        oauthValidateCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createOauthValidateCode() throws Exception {
        int databaseSizeBeforeCreate = oauthValidateCodeRepository.findAll().size();

        // Create the OauthValidateCode
        restOauthValidateCodeMockMvc.perform(post("/api/oauth-validate-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oauthValidateCode)))
            .andExpect(status().isCreated());

        // Validate the OauthValidateCode in the database
        List<OauthValidateCode> oauthValidateCodeList = oauthValidateCodeRepository.findAll();
        assertThat(oauthValidateCodeList).hasSize(databaseSizeBeforeCreate + 1);
        OauthValidateCode testOauthValidateCode = oauthValidateCodeList.get(oauthValidateCodeList.size() - 1);
        assertThat(testOauthValidateCode.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOauthValidateCode.getPrincipal()).isEqualTo(DEFAULT_PRINCIPAL);
        assertThat(testOauthValidateCode.getExpireTime()).isEqualTo(DEFAULT_EXPIRE_TIME);
        assertThat(testOauthValidateCode.getCodeType()).isEqualTo(DEFAULT_CODE_TYPE);
    }

    @Test
    @Transactional
    public void createOauthValidateCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = oauthValidateCodeRepository.findAll().size();

        // Create the OauthValidateCode with an existing ID
        oauthValidateCode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOauthValidateCodeMockMvc.perform(post("/api/oauth-validate-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oauthValidateCode)))
            .andExpect(status().isBadRequest());

        // Validate the OauthValidateCode in the database
        List<OauthValidateCode> oauthValidateCodeList = oauthValidateCodeRepository.findAll();
        assertThat(oauthValidateCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOauthValidateCodes() throws Exception {
        // Initialize the database
        oauthValidateCodeRepository.saveAndFlush(oauthValidateCode);

        // Get all the oauthValidateCodeList
        restOauthValidateCodeMockMvc.perform(get("/api/oauth-validate-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oauthValidateCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.toString())))
            .andExpect(jsonPath("$.[*].expireTime").value(hasItem(sameInstant(DEFAULT_EXPIRE_TIME))))
            .andExpect(jsonPath("$.[*].codeType").value(hasItem(DEFAULT_CODE_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getOauthValidateCode() throws Exception {
        // Initialize the database
        oauthValidateCodeRepository.saveAndFlush(oauthValidateCode);

        // Get the oauthValidateCode
        restOauthValidateCodeMockMvc.perform(get("/api/oauth-validate-codes/{id}", oauthValidateCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(oauthValidateCode.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.toString()))
            .andExpect(jsonPath("$.expireTime").value(sameInstant(DEFAULT_EXPIRE_TIME)))
            .andExpect(jsonPath("$.codeType").value(DEFAULT_CODE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOauthValidateCode() throws Exception {
        // Get the oauthValidateCode
        restOauthValidateCodeMockMvc.perform(get("/api/oauth-validate-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOauthValidateCode() throws Exception {
        // Initialize the database
        oauthValidateCodeRepository.saveAndFlush(oauthValidateCode);
        int databaseSizeBeforeUpdate = oauthValidateCodeRepository.findAll().size();

        // Update the oauthValidateCode
        OauthValidateCode updatedOauthValidateCode = oauthValidateCodeRepository.findOne(oauthValidateCode.getId());
        // Disconnect from session so that the updates on updatedOauthValidateCode are not directly saved in db
        em.detach(updatedOauthValidateCode);
        updatedOauthValidateCode
            .code(UPDATED_CODE)
            .principal(UPDATED_PRINCIPAL)
            .expireTime(UPDATED_EXPIRE_TIME)
            .codeType(UPDATED_CODE_TYPE);

        restOauthValidateCodeMockMvc.perform(put("/api/oauth-validate-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOauthValidateCode)))
            .andExpect(status().isOk());

        // Validate the OauthValidateCode in the database
        List<OauthValidateCode> oauthValidateCodeList = oauthValidateCodeRepository.findAll();
        assertThat(oauthValidateCodeList).hasSize(databaseSizeBeforeUpdate);
        OauthValidateCode testOauthValidateCode = oauthValidateCodeList.get(oauthValidateCodeList.size() - 1);
        assertThat(testOauthValidateCode.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOauthValidateCode.getPrincipal()).isEqualTo(UPDATED_PRINCIPAL);
        assertThat(testOauthValidateCode.getExpireTime()).isEqualTo(UPDATED_EXPIRE_TIME);
        assertThat(testOauthValidateCode.getCodeType()).isEqualTo(UPDATED_CODE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingOauthValidateCode() throws Exception {
        int databaseSizeBeforeUpdate = oauthValidateCodeRepository.findAll().size();

        // Create the OauthValidateCode

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOauthValidateCodeMockMvc.perform(put("/api/oauth-validate-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oauthValidateCode)))
            .andExpect(status().isCreated());

        // Validate the OauthValidateCode in the database
        List<OauthValidateCode> oauthValidateCodeList = oauthValidateCodeRepository.findAll();
        assertThat(oauthValidateCodeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOauthValidateCode() throws Exception {
        // Initialize the database
        oauthValidateCodeRepository.saveAndFlush(oauthValidateCode);
        int databaseSizeBeforeDelete = oauthValidateCodeRepository.findAll().size();

        // Get the oauthValidateCode
        restOauthValidateCodeMockMvc.perform(delete("/api/oauth-validate-codes/{id}", oauthValidateCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OauthValidateCode> oauthValidateCodeList = oauthValidateCodeRepository.findAll();
        assertThat(oauthValidateCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OauthValidateCode.class);
        OauthValidateCode oauthValidateCode1 = new OauthValidateCode();
        oauthValidateCode1.setId(1L);
        OauthValidateCode oauthValidateCode2 = new OauthValidateCode();
        oauthValidateCode2.setId(oauthValidateCode1.getId());
        assertThat(oauthValidateCode1).isEqualTo(oauthValidateCode2);
        oauthValidateCode2.setId(2L);
        assertThat(oauthValidateCode1).isNotEqualTo(oauthValidateCode2);
        oauthValidateCode1.setId(null);
        assertThat(oauthValidateCode1).isNotEqualTo(oauthValidateCode2);
    }
}
