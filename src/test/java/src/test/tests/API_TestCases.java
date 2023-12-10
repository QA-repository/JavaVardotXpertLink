package src.test.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.annotations.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class API_TestCases extends TestBases {
    static ConfigurationReader CR=new ConfigurationReader();


    @Test(priority = 1,groups = { "Sanity" })
    public void Read_File_Test_DONFH_AddCamp() throws Exception {

        // Use ConfigurationReader to read JSON file and get parameters
        String jsonFilePath = "C:\\Users\\Vardot\\Documents\\VardotAutomationFramework\\JavaVardotXpertLink\\XHR Files\\BasicPage.json";
        Map<String, String> paramsMap = CR.readJsonFile(jsonFilePath);


        // Build RequestBody map
        Map<String, String> RequestBody = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {

            if ("form_build_id".equals(entry.getKey())){

                RequestBody.put("form_build_id", APICaller.PrepareFormData(CR.GetRun_ENV()+CR.properties.getProperty("Donation_ADD_FH_Camp_Path"),"input[name=form_build_id]"));
                System.out.println("build_id");

            }

            if ("form_token".equals(entry.getKey())){

                RequestBody.put("form_token", APICaller.PrepareFormData(CR.GetRun_ENV()+CR.properties.getProperty("Donation_ADD_FH_Camp_Path"),"input[name=form_token]"));
                System.out.println("form_token");

            }else

                RequestBody.put(entry.getKey(), entry.getValue());


        }
        APICaller.ContentCreationAPI(CR.GetRun_ENV()+CR.properties.getProperty("Donation_ADD_FH_Camp_Path"),RequestBody,CR.Getcookie());
    }


    @Test(groups = { "Sanity" })
    public void ISG_Create_New_User() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();

        RequestBody.put("form_token", APICaller.PrepareFormData(CR.GETISG_DevLink() + CR.GetCreateUserPath(), "input[name=form_token]"));
        RequestBody.put("form_build_id", APICaller.PrepareFormData(CR.GETISG_DevLink() + CR.GetCreateUserPath(), "input[name=form_build_id]"));
        RequestBody.put("mail", TestBases.generateRandomString(12) + "@vardot.com");
        RequestBody.put("name", TestBases.generateRandomString(8));
        RequestBody.put("pass[pass1]", "Vardot@123");
        RequestBody.put("pass[pass2]", "Vardot@123");
        RequestBody.put("status", "1");
        RequestBody.put("roles[editor]", "editor");
        RequestBody.put("roles[hr_admin]", "hr_admin");
        RequestBody.put("roles[content_admin]", "content_admin");
        RequestBody.put("roles[site_admin]", "site_admin");
        RequestBody.put("form_id", "user_register_form");
        RequestBody.put("antibot_key", "lkbr5Ra-GtUD3vHWMeslxWu0azZr3V2paXoawPnU-CU");
        RequestBody.put("path[0][alias]", "");
        RequestBody.put("op", "Create new account");
        APICaller.ContentCreationAPI(CR.GETISG_DevLink() + CR.GetCreateUserPath(), RequestBody, CR.Getcookie());

    }
    @Test(groups = { "Sanity" })
    public void ISG_Search() throws Exception {
        List<String> RequestHeader= new ArrayList<>();
        String Response;
        RequestHeader.add(0,"keywords");
        RequestHeader.add(1,"ASD");
        Response= String.valueOf(APICaller.Public_GetAPI_Caller(CR.GetRun_ENV(),CR.GetMainSearchPath(),RequestHeader));
        System.out.println(Response);
   APICaller.Public_GetAPI_Caller(CR.GetRun_ENV(),CR.GetMainSearchPath(),RequestHeader);
    }
    @Test(priority = 2,groups = { "Sanity" },invocationCount=2,dependsOnMethods={"Check_User_Ability_To_Create_News","Check_User_Ability_To_Create_Publication"})
    public void Delete_Content() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();
        RequestBody.put("confirm", "1");
        RequestBody.put("form_build_id", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.DeleteNodePath().replace("312", GoogleSheetsHelper.GetNodeID().replace("[", "").replace("]", "").replaceAll("[^\\d.]", "")), "input[name=form_build_id]"));
        RequestBody.put("form_token", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.DeleteNodePath().replace("312", GoogleSheetsHelper.GetNodeID().replace("[", "").replace("]", "").replaceAll("[^\\d.]", "")), "input[name=form_token]"));
        String Formid=GoogleSheetsHelper.GetNodeID()
                .replace("[", "").replace("]", "").replaceAll("[0-9]", "").replace(",", "");
        RequestBody.put("form_id",GoogleSheetsHelper.prepareNode_For_deletion(Formid) );
        RequestBody.put("op", "Delete");
        APICaller.Content_Deleter(CR.GetRun_ENV(), CR.DeleteNodePath().replace("XXXXX", GoogleSheetsHelper.GetNodeID().replace("[", "").replace("]", "").replaceAll("[^\\d.]", "")), CR.Getcookie(), RequestBody);
    }
    @Test(priority = 1,groups = { "Sanity" })
    public void Create_Basic_Page() throws Exception {


        String jsonFilePath = "C:\\Users\\Vardot\\Documents\\VardotAutomationFramework\\JavaVardotXpertLink\\XHR Files\\BasicPage.json";
        Map<String, String> paramsMap = CR.readJsonFile(jsonFilePath);

        Map<String, String> RequestBody = new LinkedHashMap<>();

        // Build RequestBody map
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {

            if ("form_build_id".equals(entry.getKey())){

                RequestBody.put("form_build_id", APICaller.PrepareFormData(CR.GetRun_ENV()+ ConfigurationReader.properties.getProperty("ISGBasicPagePath"),"input[name=form_build_id]"));
                System.out.println("build_id");

            }

            if ("form_token".equals(entry.getKey())){

                RequestBody.put("form_token", APICaller.PrepareFormData(CR.GetRun_ENV()+CR.properties.getProperty("ISGBasicPagePath"),"input[name=form_token]"));
                System.out.println("form_token");

            }else

                RequestBody.put(entry.getKey(), entry.getValue());


        }
        APICaller.ContentCreationAPI(CR.GetRun_ENV()+CR.properties.getProperty("ISGBasicPagePath"),RequestBody,CR.Getcookie());

    }
    @Test(priority = 1,groups = { "Sanity" })
    public void Add_FAQ() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();

        RequestBody.put("changed", "1701256988");
        RequestBody.put("form_build_id", "form-Jm6n6SlDkTQr-ot0coy_3249fhlHleZx94h5_wSA0LA");
        RequestBody.put("form_token", "-sC7Sx_gOOowEBZ4NGqgFrWr5vpAkbtva-jvnFxF0iI");
        RequestBody.put("form_id", "node_faq_form");
        RequestBody.put("langcode[0][value]", "en");
        RequestBody.put("title[0][value]", "Is This a test?");
        RequestBody.put("body[0][summary]", "");
        RequestBody.put("body[0][value]", "<p>Yes it is</p>");
        RequestBody.put("body[0][format]", "full_html");
        RequestBody.put("field_category", "11");
        RequestBody.put("field_school", "32");
        RequestBody.put("group_tabs[group_tabs__active_tab]", "edit-group-basic-info");
        RequestBody.put("revision_log[0][value]", "");
        RequestBody.put("uid[0][target_id]", "Ibrahim QA (6)");
        RequestBody.put("created[0][value][date]", "2023-11-29");
        RequestBody.put("created[0][value][time]", "14:27:34");
        RequestBody.put("moderation_state[0][state]", "draft");
        RequestBody.put("op", "Save");
        APICaller.ContentCreationAPI(CR.GetRun_ENV()+ CR.ISGFAQAdd(),RequestBody,CR.Getcookie());

    }

    @Test(priority = 1,groups = { "Sanity" })
    public void Add_Image() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();

        RequestBody.put("field_media_image[0][fids]", "\"C:\\Users\\Vardot\\Downloads\\Images\\RF1247510_DSC06979_800.jpg\"");
        RequestBody.put("field_media_image[0][focal_point]", "50,50");
        RequestBody.put("form_build_id", "form-FCadS8tWeT8oSZmMusRh3vqU5cBhxmu39I7YY9fL6p4");
        RequestBody.put("form_token", "X_dtAHAorsHARzLdRheEjp_iDqNfxx2z4DJ9mxDtQzI");
        RequestBody.put("form_id", "media_image_add_form");
        RequestBody.put("field_image_description[0][value]", "Automation Image");
        RequestBody.put("name[0][value]", "AutoImage");
        RequestBody.put("field_media_in_library[value]", "1");
        RequestBody.put("status[value]", "1");
        RequestBody.put("advanced__active_tab", "edit-revision-information");
        RequestBody.put("op", "Save");
        APICaller.ContentCreationAPI(CR.GetRun_ENV()+ CR.ISGImageAdd(),RequestBody,CR.Getcookie());

    }
    @Test(priority = 1,groups = { "Sanity" })
    public void Add_MainNav() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();

        RequestBody.put("title[0][value]", TestBases.generateRandomString(10));
        RequestBody.put("link[0][uri]", "<front>");
        RequestBody.put("link[0][options][attributes][target]", "");
        RequestBody.put("link[0][options][attributes][rel]", "");
        RequestBody.put("link[0][options][attributes][class]", "");
        RequestBody.put("enabled[value]", "1");
        RequestBody.put("description[0][value]", "");
        RequestBody.put("form_build_id", "form-dfrSHgEnTPSt5AyMSOsMl6lpByjw1oHXfwjgTC9_2Pg");
        RequestBody.put("form_token", "ssiwIA3LiXCTpBO557sJ3Qc3Bgco6V7XLZWqUaNrnNI");
        RequestBody.put("form_id", "menu_link_content_main_form");
        RequestBody.put("langcode[0][value]", "en");
        RequestBody.put("menu_parent", "main:");
        RequestBody.put("weight[0][value]", "0");
        RequestBody.put("op", "Save");
        RequestBody.put("name", "Ibrahim QA");
        APICaller.ContentCreationAPI(CR.GetRun_ENV()+ CR.ISGMainNavAdd(),RequestBody,CR.Getcookie());

    }
    @Test(priority = 1,groups = { "Sanity" })
    public void Add_LandingPage() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();

        RequestBody.put("changed", "1701765189");
        RequestBody.put("form_build_id", "form-aTj0uSz1KlV8wTVRfnNnr4IL-51YTYMTV_WrrSK7sDg");
        RequestBody.put("form_token", "v3ZoUJSsyqBnwCb86_iPEC3ps631y1rWuES1Jl444tA");
        RequestBody.put("form_id", "node_landing_page_lb_form");
        RequestBody.put("langcode[0][value]", "en");
        RequestBody.put("title[0][value]", TestBases.generateRandomString(10));
        RequestBody.put("body[0][value]", "");
        RequestBody.put("body[0][format]", "full_html");
        RequestBody.put("field_description[0][value]", "");
        RequestBody.put("field_menu_media[media_library_selection]", "");
        RequestBody.put("field_link[0][uri]", "");
        RequestBody.put("field_link[0][title]", "");
        RequestBody.put("group_wrapper[group_wrapper__active_tab]", "edit-group-b");
        RequestBody.put("field_yoast_seo[0][yoast_seo][focus_keyword]", "");
        RequestBody.put("field_yoast_seo[0][yoast_seo][status]", "0");
        RequestBody.put("layout_selection", "_none");
        RequestBody.put("revision_log[0][value]", "");
        RequestBody.put("field_page_header_style", "media_header");
        RequestBody.put("field_media[media_library_selection]", "");
        RequestBody.put("uid[0][target_id]", "Ibrahim QA (6)");
        RequestBody.put("created[0][value][date]", "2023-12-05");
        RequestBody.put("created[0][value][time]", "11:33:09");
        RequestBody.put("field_meta_tags[0][basic][title]", "[node:title] | [site:name]");
        RequestBody.put("field_meta_tags[0][basic][description]", "[node:summary]");
        RequestBody.put("field_meta_tags[0][basic][abstract]", "[node:summary]");
        RequestBody.put("field_meta_tags[0][basic][keywords]", "");
        RequestBody.put("menu[title]", "");
        RequestBody.put("menu[description]", "");
        RequestBody.put("menu[menu_parent]", "main:");
        RequestBody.put("menu[weight]", "0");
        RequestBody.put("simple_sitemap[default][index]", "1");
        RequestBody.put("simple_sitemap[default][priority]", "0.5");
        RequestBody.put("simple_sitemap[default][changefreq]", "");
        RequestBody.put("simple_sitemap[default][include_images]", "0");
        RequestBody.put("path[0][pathauto]", "1");
        RequestBody.put("moderation_state[0][state]", "draft");
        RequestBody.put("op", "Save");
        APICaller.ContentCreationAPI(CR.GetRun_ENV()+ CR.ISGLandingPageAdd(),RequestBody,CR.Getcookie());

    }
    @Test(groups = { "Sanity" })
    public void Site_Main_Search() throws Exception {
        List<String> RequestHeader= new ArrayList<>();
        List<String> Response=new ArrayList<>();

        RequestHeader.add(0,"rows");
        RequestHeader.add(1,"6000");

        RequestHeader.add(2,"e1");
        RequestHeader.add(3,"1");

       Response=APICaller.Public_GetAPI_Caller(CR.GetRun_ENV(),CR.GetMainSearchPath(),RequestHeader).getBody().jsonPath().getList("response.docs.id");
        APICaller.GeneralResponseValidator(Response);
    }


    @Test(groups = { "Sanity" })
    public void News_Search() throws Exception {
        List<String> RequestHeader= new ArrayList<>();
        List<String> Response;
        RequestHeader.add(0,"q");
        RequestHeader.add(1,"*jordan:*");
        RequestHeader.add(2,"fq");
        RequestHeader.add(3,"ss_type:(\"news\")");
        RequestHeader.add(4,"fq");
        RequestHeader.add(5,"ss_search_api_language:en");
        RequestHeader.add(6,"fq");
        RequestHeader.add(7,"sm_site_name:(\"Global site\")");
        RequestHeader.add(8,"facet.field");
        RequestHeader.add(9,"sm_site_name");
        RequestHeader.add(10,"facet.field");
        RequestHeader.add(11,"ss_news_category");
        RequestHeader.add(12,"facet.field");
        RequestHeader.add(13,"ss_type");
        RequestHeader.add(14,"sm_name_1");
        RequestHeader.add(15,"sm_region");
        RequestHeader.add(16,"sm_countries");
        RequestHeader.add(17,"facet.field");
        RequestHeader.add(18,"ds_created");
        RequestHeader.add(19,"rows");
        RequestHeader.add(20,"5000");
        RequestHeader.add(21,"facet.limit");
        RequestHeader.add(22,"-1");
        RequestHeader.add(23,"start");
        RequestHeader.add(24,"0");
        RequestHeader.add(25,"facet");
        RequestHeader.add(26,"on");
        RequestHeader.add(27,"hl");
        RequestHeader.add(28,"on");
        RequestHeader.add(29,"hl.fl");
        RequestHeader.add(30,"tm_X3b_en_rendered_item");
        RequestHeader.add(31,"hl.usePhraseHighlighter");
        RequestHeader.add(32,"true");
        RequestHeader.add(33,"wt");
        RequestHeader.add(34,"json");
        Response= APICaller.Public_GetAPI_Caller(CR.GetRun_ENV(),CR.GetNewsSearchPath(),RequestHeader).getBody().jsonPath().getList("response.docs.id");
        APICaller.GeneralResponseValidator(Response);

    }

    @Test(priority = 2,groups = { "Sanity" },invocationCount=2,dependsOnMethods={"Check_User_Ability_To_Create_News","Check_User_Ability_To_Create_Publication"})
    public void Check_User_Ability_To_Delete_Content() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();
        RequestBody.put("confirm", "1");
        RequestBody.put("form_build_id", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.DeleteNodePath().replace("XXXXX", GoogleSheetsHelper.GetNodeID().replace("[", "").replace("]", "").replaceAll("[^\\d.]", "")), "input[name=form_build_id]"));
        RequestBody.put("form_token", APICaller.PrepareFormData(CR.GetRun_ENV() + CR.DeleteNodePath().replace("XXXXX", GoogleSheetsHelper.GetNodeID().replace("[", "").replace("]", "").replaceAll("[^\\d.]", "")), "input[name=form_token]"));
        String Formid=GoogleSheetsHelper.GetNodeID()
                .replace("[", "").replace("]", "").replaceAll("[0-9]", "").replace(",", "");
        RequestBody.put("form_id",GoogleSheetsHelper.prepareNode_For_deletion(Formid) );
        RequestBody.put("op", "Delete");
        APICaller.Content_Deleter(CR.GetRun_ENV(), CR.DeleteNodePath().replace("XXXXX", GoogleSheetsHelper.GetNodeID().replace("[", "").replace("]", "").replaceAll("[^\\d.]", "")), CR.Getcookie(), RequestBody);
    }


    @Test(priority = 3,groups = { "Sanity" })
    public void Check_User_Ability_To_Create_Event() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();

        RequestBody.put("changed", "1695558430");
        RequestBody.put("langcode[0][value]", "en");
        // to be completed by hamza/ibrahim
    }
 }

