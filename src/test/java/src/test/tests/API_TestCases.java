package src.test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class API_TestCases extends TestBases {
    static ConfigurationReader CR = new ConfigurationReader();

    public static void main(String[] args) throws Exception {
        API_TestCases taxonomyCreator = new API_TestCases();

        String[] keys = {
                "langcode[0][value]",
                "name[0][value]",
                "description[0][value]",
                "description[0][format]",
                "field_cname[0][value]",
                "field_search_tooltip[0][value]",
                "field_cid[0][value]",
                "group_tabs[group_tabs__active_tab]",
                "form_build_id",
                "form_token",
                "form_id",
                "parent[]",
                "weight",
                "path[0][alias]",
                "status[value]",
                "op"
        };
        String[] values = {
                "en",
                "Automation_Term_Test",
                "Automation_Description",
                "full_html",
                "Automation_Code",
                "",
                "",
                "edit-group-basic-info",
                APICaller.PrepareFormData(CR.GetRun_ENV() + CR.GetTaxonomyPath(), "input[name=form_build_id]"),
                APICaller.PrepareFormData(CR.GetRun_ENV() + CR.GetTaxonomyPath(), "input[name=form_token]"),
                "taxonomy_term_collection_form",
                "0",
                "0",
                "",
                "1",
                "Save"
        };

        // Call the addTaxonomyTerm method with the keys and values arrays
        //taxonomyCreator.addTaxonomyTerm(keys, values);
        //taxonomyCreator.cloneTxonomyTerm ("3688");
        taxonomyCreator.deleteTaxonomyTerm("3689");
        //taxonomyCreator.editTxonomyTerm(keys, values, "3688");

    }

    public void addTaxonomyTerm(String[] keys, String[] values) throws Exception {
        if (keys.length != values.length) {
            throw new IllegalArgumentException("Keys and values arrays must have the same length");
        }
        Map<String, String> requestBody = new LinkedHashMap<>();
        for (int i = 0; i < keys.length; i++) {
            requestBody.put(keys[i], values[i]);
        }
        APICaller.ContentCreationAPI(CR.GetRun_ENV() + CR.GetTaxonomyPath(), requestBody, CR.Getcookie());
    }

    public void deleteTaxonomyTerm(String NodeID) throws Exception {
        List<String> RequestHeader = new ArrayList();
        Map<String, String> RequestBody = new LinkedHashMap();
        RequestBody.put("confirm", "1");
        String var10002 = CR.GetRun_ENV();
        RequestBody.put("form_build_id", APICaller.PrepareFormData(var10002 + CR.DeleteTaxonomyPath().replace("XXXXX", NodeID.replace("[", "").replace("]", "").replaceAll("[^\\d.]", "")), "input[name=form_build_id]"));
        var10002 = CR.GetRun_ENV();
        RequestBody.put("form_token", APICaller.PrepareFormData(var10002 + CR.DeleteTaxonomyPath().replace("XXXXX", NodeID.replace("[", "").replace("]", "").replaceAll("[^\\d.]", "")), "input[name=form_token]"));
        RequestBody.put("form_id", "taxonomy_term_collection_delete_form");
        RequestBody.put("op", "Delete");
        APICaller.Content_Deleter(CR.GetRun_ENV(), CR.DeleteTaxonomyPath().replace("XXXXX", NodeID), CR.Getcookie(), RequestBody);
    }

    public void editTxonomyTerm(String[] keys, String[] values, String NodeID) throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap();
        if (keys.length != values.length) {
            throw new IllegalArgumentException("Keys and values arrays must have the same length");
        }
        for (int i = 0; i < keys.length; i++) {
            RequestBody.put(keys[i], values[i]);
        }

        String var10002 = CR.GetRun_ENV();
        RequestBody.put("form_build_id", APICaller.PrepareFormData(var10002 + CR.EditTaxonomyNodePath().replace("XXXXX", NodeID), "input[name=form_build_id]"));
        var10002 = CR.GetRun_ENV();
        RequestBody.put("form_token", APICaller.PrepareFormData(var10002 + CR.EditTaxonomyNodePath().replace("XXXXX", NodeID), "input[name=form_token]"));

        APICaller.ContentCreationAPI(CR.GetRun_ENV() + CR.EditTaxonomyNodePath().replace("XXXXX", NodeID), RequestBody, CR.Getcookie());
    }

    @Test()
    public void Check_User_Ability_To_Create_EHBLP() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();

        RequestBody.put("changed", "1699961719");
        RequestBody.put("langcode[0][value]", "en");
        RequestBody.put("form_build_id", "form-HQZfRsko6CVSP2jEy5UmHaxVmq1dOP3XoFWyySv0_-c");
        RequestBody.put("form_token", "vxlRJQnQrsY_iRZJgWU6nyfpxClQ2AtM_CAST-lvJXI");
        RequestBody.put("form_id", "node_landing_page_lb_form");
        RequestBody.put("ajax_responsive_preview", "");
        RequestBody.put("title[0][value]", "Testing");
        RequestBody.put("field_highlight_text[0][value]", "");
        RequestBody.put("body[0][value]", "");
        RequestBody.put("body[0][format]", "full_html");
        RequestBody.put("field_metatag_description[0][value]", "");
        RequestBody.put("field_layout_style", "bar");
        RequestBody.put("field_background_color", "white-smoke-bg");
        RequestBody.put("field_description_header[0][value]", "");
        RequestBody.put("field_media[media_library_selection]", "");
        RequestBody.put("field_video_button_text[0][value]", "");
        RequestBody.put("field_pre_title[0][value]", "");
        RequestBody.put("field_links[0][uri]", "");
        RequestBody.put("field_links[0][title]", "");
        RequestBody.put("field_links[0][options][attributes][class][0]", "btn btn-primary");
        RequestBody.put("field_links[0][options][attributes][class][1]", "button-text-center");
        RequestBody.put("field_links[0][_weight]", "0");
        RequestBody.put("field_links[1][uri]", "");
        RequestBody.put("field_links[1][title]", "");
        RequestBody.put("field_links[1][options][attributes][class][0]", "btn btn-primary");
        RequestBody.put("field_links[1][options][attributes][class][1]", "button-text-center");
        RequestBody.put("field_links[1][_weight]", "1");
        RequestBody.put("field_align", "_none");
        RequestBody.put("group_tabs[group_tabs__active_tab]", "edit-group-media-header");
        RequestBody.put("field_yoast_seo[0][yoast_seo][focus_keyword]", "");
        RequestBody.put("field_yoast_seo[0][yoast_seo][status]", "0");
        RequestBody.put("revision_log[0][value]", "");
    }

    @Test(priority = 2, groups = {"Sanity"})
    public void EHB_Add_Remote_Video() throws Exception {
        LinkedHashMap<String, String> RequestBody = new LinkedHashMap<>();
        RequestBody.put("name[0][value]", TestBases.generateRandomString(12));
        RequestBody.put("form_token", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.GetEHB_Add_RemoteVideoPath(), "input[name=form_token]"));
        RequestBody.put("form_build_id", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.GetEHB_Add_RemoteVideoPath(), "input[name=form_build_id]"));
        RequestBody.put("form_id", "media_remote_video_add_form");
        RequestBody.put("field_media_oembed_video[0][value]", "https://www.youtube.com/watch?v=Z4XxrLBL_CI");
        RequestBody.put("field_media_cover_image[0][fids]", "");
        RequestBody.put("field_media_cover_image[0][focal_point]", "50,50");
        RequestBody.put("field_caption[0][value]", TestBases.generateRandomString(10));
        RequestBody.put("field_caption[0][format]", "basic_html");
        RequestBody.put("field_copyright[0][value]", TestBases.generateRandomString(5));
        RequestBody.put("field_media_in_library[value]", "1");
        RequestBody.put("status[value]", "1");
        RequestBody.put("advanced__active_tab", "edit-revision-information");
        RequestBody.put("op", "Save");
        APICaller.ContentCreationAPI(CR.GetRun_ENV() + CR.GetEHB_Add_RemoteVideoPath(), RequestBody, CR.Getcookie());

    }

    @Test(groups = {"Sanity"})
    public void EHB_Main_Search() throws Exception {
        List<String> RequestHeader = new ArrayList<>();
        String Response = "";

        RequestHeader.add(0, "search");
        RequestHeader.add(1, "UNHCR");

        RequestHeader.add(2, "sort_by");
        RequestHeader.add(3, "search_api_relevance");

        Response = String.valueOf(APICaller.Public_GetAPI_Caller(CR.GetRun_ENV(), CR.GetEHB_SearchAPI(), RequestHeader).getBody().jsonPath());

    }


//    @Test(priority = 2,groups = { "Sanity" },invocationCount=2,dependsOnMethods={"Check_User_Ability_To_Create_News","Check_User_Ability_To_Create_Publication"})
//    public void Check_User_Ability_To_Delete_Content() throws Exception {
//        Map<String, String> RequestBody = new LinkedHashMap<>();
//        RequestBody.put("confirm", "1");
//        RequestBody.put("form_build_id", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.DeleteNodePath().replace("XXXXX", GoogleSheetsHelper.GetNodeID().replace("[", "").replace("]", "").replaceAll("[^\\d.]", "")), "input[name=form_build_id]"));
//        RequestBody.put("form_token", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.DeleteNodePath().replace("XXXXX", GoogleSheetsHelper.GetNodeID().replace("[", "").replace("]", "").replaceAll("[^\\d.]", "")), "input[name=form_token]"));
//        String Formid=GoogleSheetsHelper.GetNodeID()
//                .replace("[", "").replace("]", "").replaceAll("[0-9]", "").replace(",", "");
//        RequestBody.put("form_id",GoogleSheetsHelper.prepareNode_For_deletion(Formid) );
//        RequestBody.put("op", "Delete");
//        APICaller.Content_Deleter(CR.GetRun_ENV(), CR.DeleteNodePath().replace("XXXXX", GoogleSheetsHelper.GetNodeID().replace("[", "").replace("]", "").replaceAll("[^\\d.]", "")), CR.Getcookie(), RequestBody);
//    }


    @Test(priority = 2, groups = {"Sanity"})
    public void Check_User_Ability_To_Create_CCPM() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();
        RequestBody.put("status[value]", "1");
        RequestBody.put("op", "Save");
        RequestBody.put("changed", "1717412206");
        RequestBody.put("field_year[0][value]", "2023");
        RequestBody.put("field_survey_status[value]", "1");
        RequestBody.put("field_level", "15036");
        RequestBody.put("field_cluster", "15021");
        RequestBody.put("field_cluster_country[0][value]", "JO");
        RequestBody.put("field_city[0][value]", "Amman");
        RequestBody.put("field_start_date[0][value][date]", "2024-06-03");
        RequestBody.put("field_end_date[0][value][date]", "2024-06-04");
        RequestBody.put("field_language", "en");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_name][0][value]", "moawih11");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_email][0][value]", "m.jaber@vardot.com");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_type_of_contract]", "15211");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_type_of_modality]", "15241");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_grade_of_the_post]", "15041");
        RequestBody.put("field_cluster_coordinator_data[0][_weight]", "0");
        RequestBody.put("field_cluster_coordinator_data[add_more][add_more_delta]", "");
        RequestBody.put("field_im[0][subform][field_name][0][value]", "test");
        RequestBody.put("field_im[0][subform][field_email][0][value]", "test@test.com");
        RequestBody.put("field_im[0][subform][field_type_of_contract]", "15211");
        RequestBody.put("field_im[0][subform][field_type_of_modality]", "15241");
        RequestBody.put("field_im[0][subform][field_grade_of_the_post]", "15041");
        RequestBody.put("field_im[0][_weight]", "0");
        RequestBody.put("field_im[add_more][add_more_delta]", "");
        RequestBody.put("field_others[add_more][add_more_delta]", "");
        RequestBody.put("field_international_ngos[0][subform][field_partner_name][target_id][textfield]", "Acceso (15406)");
        RequestBody.put("field_international_ngos[0][_weight]", "0");
        RequestBody.put("field_international_ngos[add_more][add_more_delta]", "");
        RequestBody.put("field_icrc_ifrc[0][subform][field_partner_name][target_id][textfield]", "British Red Cross (15186)");
        RequestBody.put("field_icrc_ifrc[0][_weight]", "0");
        RequestBody.put("field_icrc_ifrc[add_more][add_more_delta]", "");
        RequestBody.put("field_un_organization[add_more][add_more_delta]", "");
        RequestBody.put("field_donors[add_more][add_more_delta]", "");
        RequestBody.put("field_national_local_authority[add_more][add_more_delta]", "");
        RequestBody.put("field_national_local_ngo_cbo[add_more][add_more_delta]", "");
        RequestBody.put("group_information[group_information__active_tab]", "edit-group-partners");
     RequestBody.put("form_build_id", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.GetCreateContent() ,"input[name=form_build_id]"));
        RequestBody.put("form_token", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.GetCreateContent(), "input[name=form_token]"));
        RequestBody.put("form_id", "node_ccpm_form");
        RequestBody.put("revision_log[0][value]", "");
        RequestBody.put("path[0][pathauto]", "1");
        APICaller.ContentCreationAPI(CR.GetRun_ENV() + CR.GetCreateContent(), RequestBody, CR.Getcookie());

    }
    @Test(priority = 1, groups = { "Sanity" })
    public void Check_User_Ability_To_Submit_CCPM_Form() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();
        RequestBody.put("status[value]", "1");
        RequestBody.put("op", "Save");
        RequestBody.put("changed", "1720016745");
        RequestBody.put("field_year[0][value]", "2000");
        RequestBody.put("field_survey_status[value]", "1");
        RequestBody.put("field_level", "15036");
        RequestBody.put("field_cluster", "15021");
        RequestBody.put("field_cluster_country[0][value]", "AL");
        RequestBody.put("field_city[0][value]", "");
        RequestBody.put("field_start_date[0][value][date]", "2024-07-04");
        RequestBody.put("field_end_date[0][value][date]", "2024-07-04");
        RequestBody.put("field_language", "en");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_name][0][value]", "moawih");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_email][0][value]", "m.jaber@vardot.com");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_type_of_contract]", "15211");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_type_of_modality]", "15241");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_grade_of_the_post]", "15041");
        RequestBody.put("field_cluster_coordinator_data[0][_weight]", "0");
        RequestBody.put("field_cluster_coordinator_data[add_more][add_more_delta]", "");
        RequestBody.put("field_im[0][subform][field_name][0][value]", "test");
        RequestBody.put("field_im[0][subform][field_email][0][value]", "test@test.com");
        RequestBody.put("field_im[0][subform][field_type_of_contract]", "15211");
        RequestBody.put("field_im[0][subform][field_type_of_modality]", "15241");
        RequestBody.put("field_im[0][subform][field_grade_of_the_post]", "15041");
        RequestBody.put("field_im[0][_weight]", "0");
        RequestBody.put("field_im[add_more][add_more_delta]", "");
        RequestBody.put("field_others[add_more][add_more_delta]", "");
        RequestBody.put("field_international_ngos[0][subform][field_partner_name][target_id][textfield]", "Access Center for Human Rights (15411)");
        RequestBody.put("field_international_ngos[0][_weight]", "0");
        RequestBody.put("field_international_ngos[add_more][add_more_delta]", "");
        RequestBody.put("field_icrc_ifrc[add_more][add_more_delta]", "");
        RequestBody.put("field_un_organization[add_more][add_more_delta]", "");
        RequestBody.put("field_donors[add_more][add_more_delta]", "");
        RequestBody.put("field_national_local_authority[add_more][add_more_delta]", "");
        RequestBody.put("field_national_local_ngo_cbo[add_more][add_more_delta]", "");
        RequestBody.put("group_information[group_information__active_tab]", "edit-group-partners");
        RequestBody.put("form_build_id", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.GetCreateContent() ,"input[name=form_build_id]"));
        RequestBody.put("form_token", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.GetCreateContent(), "input[name=form_token]"));
        RequestBody.put("form_id", "node_ccpm_form");
        RequestBody.put("revision_log[0][value]", "");
        RequestBody.put("path[0][pathauto]", "1");
        APICaller.ContentCreationAPI(CR.GetRun_ENV() + CR.GetCreateContent(), RequestBody, CR.Getcookie());

    }

    @Test(priority = 1, groups = {"Sanity"})
    public void Check_User_Ability_To_Edit_CCPM() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();
        RequestBody.put("status[value]", "1");
        RequestBody.put("op", "Save");
        RequestBody.put("changed", "1717412206");
        RequestBody.put("field_year[0][value]", "2022");
        RequestBody.put("field_survey_status[value]", "1");
        RequestBody.put("field_level", "15036");
        RequestBody.put("field_cluster", "15021");
        RequestBody.put("field_cluster_country[0][value]", "JO");
        RequestBody.put("field_city[0][value]", "Amman");
        RequestBody.put("field_start_date[0][value][date]", "2024-06-03");
        RequestBody.put("field_end_date[0][value][date]", "2024-06-04");
        RequestBody.put("field_language", "en");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_name][0][value]", "moawih11");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_email][0][value]", "m.jaber@vardot.com");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_type_of_contract]", "15211");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_type_of_modality]", "15241");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_grade_of_the_post]", "15041");
        RequestBody.put("field_cluster_coordinator_data[0][_weight]", "0");
        RequestBody.put("field_cluster_coordinator_data[add_more][add_more_delta]", "");
        RequestBody.put("field_im[0][subform][field_name][0][value]", "test");
        RequestBody.put("field_im[0][subform][field_email][0][value]", "test@test.com");
        RequestBody.put("field_im[0][subform][field_type_of_contract]", "15211");
        RequestBody.put("field_im[0][subform][field_type_of_modality]", "15241");
        RequestBody.put("field_im[0][subform][field_grade_of_the_post]", "15041");
        RequestBody.put("field_im[0][_weight]", "0");
        RequestBody.put("field_im[add_more][add_more_delta]", "");
        RequestBody.put("field_others[add_more][add_more_delta]", "");
        RequestBody.put("field_international_ngos[0][subform][field_partner_name][target_id][textfield]", "Acceso (15406)");
        RequestBody.put("field_international_ngos[0][_weight]", "0");
        RequestBody.put("field_international_ngos[add_more][add_more_delta]", "");
        RequestBody.put("field_icrc_ifrc[0][subform][field_partner_name][target_id][textfield]", "British Red Cross (15186)");
        RequestBody.put("field_icrc_ifrc[0][_weight]", "0");
        RequestBody.put("field_icrc_ifrc[add_more][add_more_delta]", "");
        RequestBody.put("field_un_organization[add_more][add_more_delta]", "");
        RequestBody.put("field_donors[add_more][add_more_delta]", "");
        RequestBody.put("field_national_local_authority[add_more][add_more_delta]", "");
        RequestBody.put("field_national_local_ngo_cbo[add_more][add_more_delta]", "");
        RequestBody.put("group_information[group_information__active_tab]", "edit-group-partners");
        RequestBody.put("form_build_id", "form-Ug9ygwisdEreJY0dsWxHS6bu8oFeITLb8FZvVf9yvK4");
        RequestBody.put("form_token", "_Xq4a5ipKmaXBCwmyFr91QOEzUpm3Vfj9XGCl2b59X0");
        RequestBody.put("form_id", "node_ccpm_form");
        RequestBody.put("revision_log[0][value]", "");
        RequestBody.put("path[0][pathauto]", "1");
        APICaller.ContentCreationAPI(CR.GetRun_ENV() + CR.GetCreateContent(), RequestBody, CR.Getcookie());

    }

    @Test(priority = 1, groups = {"Sanity"})
    public void Check_User_Ability_To_Clone_CCPM() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();
        RequestBody.put("status[value]", "1");
        RequestBody.put("op", "Save");
        RequestBody.put("changed", "1718103583");
        RequestBody.put("field_year[0][value]", "2022");
        RequestBody.put("field_survey_status[value]", "1");
        RequestBody.put("field_level", "15036");
        RequestBody.put("field_cluster", "15021");
        RequestBody.put("field_cluster_country[0][value]", "JO");
        RequestBody.put("field_city[0][value]", "Amman");
        RequestBody.put("field_start_date[0][value][date]", "2024-06-03");
        RequestBody.put("field_end_date[0][value][date]", "2024-06-04");
        RequestBody.put("field_language", "en");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_name][0][value]", "moawih11");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_email][0][value]", "m.jaber@vardot.com");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_type_of_contract]", "15211");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_type_of_modality]", "15241");
        RequestBody.put("field_cluster_coordinator_data[0][subform][field_grade_of_the_post]", "15041");
        RequestBody.put("field_cluster_coordinator_data[0][_weight]", "0");
        RequestBody.put("field_cluster_coordinator_data[add_more][add_more_delta]", "");
        RequestBody.put("field_im[0][subform][field_name][0][value]", "test");
        RequestBody.put("field_im[0][subform][field_email][0][value]", "test@test.com");
        RequestBody.put("field_im[0][subform][field_type_of_contract]", "15211");
        RequestBody.put("field_im[0][subform][field_type_of_modality]", "15241");
        RequestBody.put("field_im[0][subform][field_grade_of_the_post]", "15041");
        RequestBody.put("field_im[0][_weight]", "0");
        RequestBody.put("field_im[add_more][add_more_delta]", "");
        RequestBody.put("field_others[add_more][add_more_delta]", "");
        RequestBody.put("field_international_ngos[add_more][add_more_delta]", "");
        RequestBody.put("field_icrc_ifrc[add_more][add_more_delta]", "");
        RequestBody.put("field_un_organization[add_more][add_more_delta]", "");
        RequestBody.put("field_donors[add_more][add_more_delta]", "");
        RequestBody.put("field_national_local_authority[add_more][add_more_delta]", "");
        RequestBody.put("field_national_local_ngo_cbo[add_more][add_more_delta]", "");
        RequestBody.put("field_upload_final_report[0][fids]", "");
        RequestBody.put("group_information[group_information__active_tab]", "edit-group-general-information");
        RequestBody.put("form_build_id", "form-eAGqXTdfZlzGv_7l4gv-cs9Ep0UpqTAIjfRb5pAIg4M");
        RequestBody.put("form_token", "xYY9l3bxjeilFxZEcrcYY_vrboB2Tx1zCz9GYBqRqGU");
        RequestBody.put("form_id", "node_ccpm_quick_node_clone_form");
        RequestBody.put("revision_log[0][value]", "");
        RequestBody.put("path[0][pathauto]", "1");

        APICaller.ContentCreationAPI(CR.GetRun_ENV() + CR.GetClone_CCPM_Path(), RequestBody, CR.Getcookie());

    }

    @Test(priority = 2, groups = {"Sanity"}, invocationCount = 2, dependsOnMethods = {"Check_User_Ability_To_Create_CCPM"})
    public void Check_User_Delete_Content() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();
        RequestBody.put("confirm", "1");
        RequestBody.put("form_build_id", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.GetDeleteNodePath().replace("XXXXX", GoogleSheetsHelper.GetNodeID().replace("[", "").replace("]", "").replaceAll("[^\\d.]", "")), "input[name=form_build_id]"));
        RequestBody.put("form_token", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.GetDeleteNodePath().replace("XXXXX", GoogleSheetsHelper.GetNodeID().replace("[", "").replace("]", "").replaceAll("[^\\d.]", "")), "input[name=form_token]"));
        String Formid = GoogleSheetsHelper.GetNodeID()
                .replace("[", "").replace("]", "").replaceAll("[0-9]", "").replace(",", "");
        RequestBody.put("form_id", GoogleSheetsHelper.prepareNode_For_deletion(Formid));
        RequestBody.put("op", "Delete");
        APICaller.Content_Deleter(CR.GetRun_ENV(), CR.GetDeleteNodePath().replace("XXXXX", GoogleSheetsHelper.GetNodeID().replace("[", "").replace("]", "").replaceAll("[^\\d.]", "")), CR.Getcookie(), RequestBody);

    }


    @Test()
    public void Add_People() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();
        RequestBody.put("field_text_plain[0][value]", "test m");
        RequestBody.put("form_build_id", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.AddPeoplePath() ,"input[name=form_build_id]"));
        RequestBody.put("form_token", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.AddPeoplePath(), "input[name=form_token]"));
        RequestBody.put("form_id", "user_register_form");
        RequestBody.put("field_cluster", "");
        RequestBody.put("field_country[0][value]", "");
        RequestBody.put("mail", "");
        RequestBody.put("name", "moawih_test");
        RequestBody.put("pass[pass1]", "Admin@123");
        RequestBody.put("pass[pass2]", "Admin@123");
        RequestBody.put("status", "1");
        RequestBody.put("roles[content_manager]", "content_manager");
        RequestBody.put("op", "Create new account");
        APICaller.ContentCreationAPI(CR.GetRun_ENV() + CR.AddPeoplePath(), RequestBody, CR.Getcookie());

    }

    @Test(priority = 2, groups = {"Sanity"})
    public void Add_term_CCPM() throws Exception {
        LinkedHashMap<String, String> RequestBody = new LinkedHashMap<>();
        RequestBody.put("name[0][value]", "TISTING");
        RequestBody.put("description[0][value]", "");
        RequestBody.put("description[0][format]", "full_html");
        RequestBody.put("form_build_id", "form-QRInbyb8t_CIJHK0c7EV_Yj2bufrCKV2QcMgr5_0xKA");
        RequestBody.put("form_token", "gY6g-atVu7GmjINsG2rQZQpc6k4D1zK6ff2qyIzGcrY");
        RequestBody.put("form_id", "taxonomy_term_type_of_resources_form");
        RequestBody.put("parent[]", "0");
        RequestBody.put("weight", "0");
        RequestBody.put("revision_log_message[0][value]", "");
        RequestBody.put("path[0][alias]", "");
        RequestBody.put("advanced__active_tab", "edit-revision-information");
        RequestBody.put("status[value]", "1");
        RequestBody.put("op", "Save.");
        APICaller.ContentCreationAPI(CR.GetRun_ENV() + CR.add_term(), RequestBody, CR.Getcookie());

    }

    @Test(priority = 2, groups = {"Sanity"})
    public void Edit_term_CCPM() throws Exception {
        LinkedHashMap<String, String> RequestBody = new LinkedHashMap<>();
        RequestBody.put("name[0][value]", "MOAWIH112233");
        RequestBody.put("description[0][value]", "<p>TEST</p>");
        RequestBody.put("description[0][format]", "full_html");
        RequestBody.put("form_build_id", "form-v8AoU0jjG_kaIZnJBJoNpuLR4P5gEZ7PrqYYV5iuTx8");
        RequestBody.put("form_token", "gY6g-atVu7GmjINsG2rQZQpc6k4D1zK6ff2qyIzGcrY");
        RequestBody.put("form_id", "taxonomy_term_type_of_resources_form");
        RequestBody.put("parent[]", "0");
        RequestBody.put("weight", "0");
        RequestBody.put("revision_log_message[0][value]", "");
        RequestBody.put("path[0][alias]", "");
        RequestBody.put("advanced__active_tab", "edit-revision-information");
        RequestBody.put("status[value]", "1");
        RequestBody.put("op", "Save");
        APICaller.ContentCreationAPI(CR.GetRun_ENV() + CR.Edit_term(), RequestBody, CR.Getcookie());

    }

    @Test(priority = 2, groups = {"Sanity"})
    public void DELETE_term_1CCPM() throws Exception {
        LinkedHashMap<String, String> RequestBody = new LinkedHashMap<>();
        String url = CR.GetRun_ENV() + CR.GetCCPM_DeleteTaxonomyPath().replace("XXXXX", GoogleSheetsHelper.GetNodeID().replace("[", "").replace("]", "").replaceAll("[^\\d.]", ""));
        String formToken = APICaller.PrepareFormData(url, "input[name=form_token]");
        String formBuildId = APICaller.PrepareFormData(url, "input[name=form_build_id]");
        RequestBody.put("confirm", "1");
        RequestBody.put("form_build_id", formBuildId);
        RequestBody.put("form_token", formToken);
        RequestBody.put("form_id", "taxonomy_term_type_of_resources_delete_form");
        RequestBody.put("op", "Delete");
        APICaller.Content_Deleter(CR.GetRun_ENV(), CR.GetCCPM_DeleteTaxonomyPath().replace("XXXXX", GoogleSheetsHelper.GetNodeID().replace("[", "").replace("]", "").replaceAll("[^\\d.]", "")), CR.Getcookie(), RequestBody);
    }

    @Test(priority = 1, groups = {"Sanity"})
    public void Check_User_Ability_To_Perform_Search() throws Exception {
        Map<String, String> requestBody = new LinkedHashMap<>();
        requestBody.put("_wrapper_format", "drupal_ajax");
        requestBody.put("search_api_fulltext", "Thematic Areas");
        requestBody.put("view_name", "search_by");
        requestBody.put("view_display_id", "page_1");
        requestBody.put("view_args", "");
        requestBody.put("view_path", "/search-by");
        requestBody.put("view_base_path", "search-by");
        requestBody.put("view_dom_id", "9825bce01e6ce0c7c59c98f3d05b56b5fd41019cbd9af1ef497679e503eade7e");
        requestBody.put("pager_element", "0");
        Response response = APICaller.Public_PostAPI_Caller(CR.GetRun_ENV(), CR.GetCCPM_SearchAPI(), requestBody, CR.Getcookie());
        String responseBody = response.getBody().asString();
        System.out.println("Response: " + responseBody);
    }

    @Test(priority = 2, groups = {"Sanity"})
    public void CCPM_Add_Remote_Video() throws Exception {
        LinkedHashMap<String, String> RequestBody = new LinkedHashMap<>();
        String formToken = APICaller.PrepareFormData(CR.GetRun_ENV() + CR.GetCCPM_Add_RemoteVideoPath(), "input[name=form_token]");
        String formBuildId = APICaller.PrepareFormData(CR.GetRun_ENV() + CR.GetCCPM_Add_RemoteVideoPath(), "input[name=form_build_id]");
        RequestBody.put("field_media_oembed_video[0][value]", "https://youtu.be/LXb3EKWsInQ?si=ril8UGxeVU8iD2Ed");
        RequestBody.put("form_build_id", formBuildId);
        RequestBody.put("form_token", formToken);
        RequestBody.put("form_id", "media_remote_video_add_form");
        RequestBody.put("field_title[0][value]", "C:\\Users\\Vardot\\Downloads\\Example File\\Image_test.png");
        RequestBody.put("field_cover_image[0][alt]", "image");
        RequestBody.put("field_cover_image[0][fids]", "53381");
        RequestBody.put("status[value]", "1");
        RequestBody.put("advanced__active_tab", "edit-revision-information");
        RequestBody.put("op", "Save");
        APICaller.ContentCreationAPI(CR.GetRun_ENV() + CR.GetCCPM_Add_RemoteVideoPath(), RequestBody, CR.Getcookie());
    }

    @Test(priority = 2, groups = {"Sanity"})
    public void Add_Entityqueues() throws Exception {
        LinkedHashMap<String, String> RequestBody = new LinkedHashMap<>();
        RequestBody.put("form_build_id", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.GetAdd_Entityqueues_Path() ,"input[name=form_build_id]"));
        RequestBody.put("form_token", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.GetAdd_Entityqueues_Path(), "input[name=form_token]"));
        RequestBody.put("form_id", "entity_subqueue_home_page_ccpm_news_edit_form");
        RequestBody.put("items[0][_weight]", "0");
        RequestBody.put("items[1][_weight]", "1");
        RequestBody.put("items[add_more][new_item][target_id]", "Lorem Ipsum (33031)");
        RequestBody.put("_triggering_element_name", "items_add_more");
        RequestBody.put("_triggering_element_value", "Add item");
        RequestBody.put("_drupal_ajax", "1");
        RequestBody.put("ajax_page_state[theme]", "gin");
        RequestBody.put("ajax_page_state[theme_token]", "sdXzrA8QT5KBo0EdprSTh9h7L-DzOOrvxM30SbOKagA");
        RequestBody.put("ajax_page_state[libraries]", "eJx9UltygzAMvJCDz9CTeIRRQMWvSoY8Tl85hHSStP1gVt5dy1hrGCIlV3MOPbC9Y1cZ0cCfUjflFZlSxVSfbU4Q2E92gxetoTwakc_J9DS6QgXtXhjvS3T1hDCLbfVB6iUoHYCzHUPuIdwoSqPRDhXPdYFgB14KhO6HOahjlv8t919RE-Mug6-04m33E1-hDzgwjO8so5ScRLdtWhsMJ1U_vxbkS3fMHDclaQWBrmhwcT7nmVAhlkCQPNrfSDfgEZZQzUjJ6rejA-_b-PfllJmuejUI-7wfEiXafFLJz5dWupgHZKiUkxMasCUcQbtTLKzhDi7qmaRxBvTVUhTz8hBe1x2Kh4IfLXGzCPI-olbfwhazAvcgeiWQqc_Aw-M1mJXwJCpUcHgumat9Y74BZXkL6Q");
        APICaller.ContentCreationAPI(CR.GetRun_ENV() + CR.GetAdd_Entityqueues_Path(), RequestBody, CR.Getcookie());
    }

    @Test(priority = 2, groups = {"Sanity"})
    public void Add_Entityqueues1() throws Exception {
        LinkedHashMap<String, String> RequestBody = new LinkedHashMap<>();


        String url = CR.GetRun_ENV() + CR.GetAdd_Entityqueues_Path();
        String formToken = APICaller.PrepareFormData(url, "input[name=form_token]");
        String formBuildId = APICaller.PrepareFormData(url, "input[name=form_build_id]");
        RequestBody.put("form_build_id", formBuildId);
        RequestBody.put("form_token", formToken);
        RequestBody.put("form_id", "entity_subqueue_home_page_ccpm_news_edit_form");
        RequestBody.put("items[0][_weight]", "0");
        RequestBody.put("items[1][_weight]", "1");
        RequestBody.put("items[add_more][new_item][target_id]", "Lorem Ipsum (33031)");
        RequestBody.put("_triggering_element_name", "items_add_more");
        RequestBody.put("_triggering_element_value", "Add item");
        RequestBody.put("_drupal_ajax", "1");
        RequestBody.put("ajax_page_state[theme]", "gin");
        RequestBody.put("ajax_page_state[theme_token]", "sdXzrA8QT5KBo0EdprSTh9h7L-DzOOrvxM30SbOKagA");
        RequestBody.put("ajax_page_state[libraries]", "eJx9UltygzAMvJCDz9CTeIRRQMWvSoY8Tl85hHSStP1gVt5dy1hrGCIlV3MOPbC9Y1cZ0cCfUjflFZlSxVSfbU4Q2E92gxetoTwakc_J9DS6QgXtXhjvS3T1hDCLbfVB6iUoHYCzHUPuIdwoSqPRDhXPdYFgB14KhO6HOahjlv8t919RE-Mug6-04m33E1-hDzgwjO8so5ScRLdtWhsMJ1U_vxbkS3fMHDclaQWBrmhwcT7nmVAhlkCQPNrfSDfgEZZQzUjJ6rejA-_b-PfllJmuejUI-7wfEiXafFLJz5dWupgHZKiUkxMasCUcQbtTLKzhDi7qmaRxBvTVUhTz8hBe1x2Kh4IfLXGzCPI-olbfwhazAvcgeiWQqc_Aw-M1mJXwJCpUcHgumat9Y74BZXkL6Q");

        APICaller.ContentCreationAPI(CR.GetRun_ENV() + CR.GetAdd_Entityqueues_Path(), RequestBody, CR.Getcookie());
    }
    
    @Test(priority = 1, groups = { "Sanity" })
    public void Check_User_Ability_To_Add_Menu_Link() throws Exception {
        Map<String, String> requestBody = new LinkedHashMap<>();
        requestBody.put("title[0][value]", "moawih");
        requestBody.put("link[0][uri]", "https://www.google.co.uk/");
        requestBody.put("enabled[value]", "1");
        requestBody.put("form_build_id", "form-me7qJSpof31qO44InbWOTS1ZytC7CupgDQltaslr_0I");
        requestBody.put("form_token", "gHCmPxdJjmP-HUExByuBVLri1SBpyxth2s_2icAkIH4");
        requestBody.put("form_id", "menu_link_content_menu_link_content_form");
        requestBody.put("menu_parent", "footer:");
        requestBody.put("weight[0][value]", "0");
        requestBody.put("description[0][value]", "");
        requestBody.put("op", "Save");
        String response = APICaller.ContentCreationAPI(CR.GetRun_ENV() + CR.GetCreate_Menu_Link_Endpoint(), requestBody, CR.Getcookie());

    }
    @Test(priority = 1, groups = { "Sanity" })
    public void addEntityqueue (String[] keys, String[] values) throws Exception {
        if (keys.length != values.length) {
            throw new IllegalArgumentException("Keys and values arrays must have the same length");
        }
        Map<String, String> requestBody = new LinkedHashMap<>();
        for (int i = 0; i < keys.length; i++) {
            requestBody.put(keys[i], values[i]);
        }
        APICaller.ContentCreationAPI(CR.GetRun_ENV() + CR.GetAdd_Entityqueues_Path(), requestBody, CR.Getcookie());
    }

}
