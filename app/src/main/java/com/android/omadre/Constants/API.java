package com.android.omadre.Constants;

/**
 * Created by divyanshu.jain on 9/15/2016.
 */
public interface API {

    String BASE = "http://18.221.155.117:2000/";
    String MOTHERS = BASE + "mothers";
    String MOTHERS_LOGIN = MOTHERS + "/login";
    String MOTHER_DETAIL = MOTHERS + "/%s";
    String MEMBERSHIP = BASE + "memberships";
    String REQUEST_QR_CODE = BASE+"requestedQRcodes?motherId=%s";


    String MOTHER_UPDATE = MOTHER_DETAIL; /*put call*/
    String ADDRESS_UPDATE = BASE + "addresses/%d";
    String PATIENTS = BASE + "patients";
    String PATIENT_INFO = PATIENTS + "/%d";
    String CREATE_RECORD = BASE + "records";
    String PATIENT_RECORDS = CREATE_RECORD + "/patientid/%d";
    String DELETE_RECORD = CREATE_RECORD + "/%d";
    String BOTTLE_INFO = BASE + "bottleInformations/";
    String BOTTLE_INFO_CURRENT = BOTTLE_INFO + "first";
    String FEED_RECORD_LISTING = BASE + "bottleinformations?motherId=%s";
    String UPDATE_MOTHER_FEED_BOTTLE = BOTTLE_INFO + "id/%d";
    String GET_MOTHER_GALLERY = BASE + "gallery/motherId/%s";
    String GET_RECORD_BY_PATIENT_ID = BASE + "records/patientId/%s";
    String MOTHER_GRAPH = BASE + "graph/mother/%s";
}
