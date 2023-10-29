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

    @Test
    public void ISG_Create_New_User() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();

        RequestBody.put("form_token", APICaller.PrepareFormData(CR.GETISG_DevLink() +CR.GetCreateUserPath(),"input[name=form_token]"));
        RequestBody.put("form_build_id", APICaller.PrepareFormData(CR.GETISG_DevLink() +CR.GetCreateUserPath(),"input[name=form_build_id]"));
        RequestBody.put("mail", TestBases.generateRandomString(12)+"@vardot.com");
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
        RequestBody.put("changed", "1691304919");
        APICaller.ContentCreationAPI(CR.GETISG_DevLink()+ CR.GetCreateUserPath(),RequestBody,CR.Getcookie());

    }
    @Test
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


    @Test
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

    @Test(priority = 1,groups = { "Sanity" })
    public void Check_User_Ability_To_Create_Publication() throws Exception {

        Map<String, String> RequestBody = new LinkedHashMap<>();
        RequestBody.put("changed", "1691304919");
        RequestBody.put("BLAB LABLA", "1691304919");

        RequestBody.put("langcode[0][value]", "en");
        RequestBody.put("form_build_id", APICaller.PrepareFormData(CR.GetRun_ENV()+ CR.GetCreatePublicationPath(),"input[name=form_build_id]"));
       RequestBody.put("form_token", APICaller.PrepareFormData(CR.GetRun_ENV()+ CR.GetCreatePublicationPath(),"input[name=form_token]"));
        RequestBody.put("form_id", "node_publication_form");
        RequestBody.put("ajax_responsive_preview", "");
        RequestBody.put("title[0][value]", TestBases.generateRandomString(12));
        RequestBody.put("field_citation[0][value]", "");
        RequestBody.put("body[0][summary]", TestBases.generateRandomString(8));
        RequestBody.put("body[0][value]", TestBases.generateRandomString(7));
        RequestBody.put("body[0][format]", "full_html");
        RequestBody.put("field_brief[0][value]", "");
        RequestBody.put("field_date_format", "full-date");
        RequestBody.put("field_layout_style", "publication-cover");
        RequestBody.put("field_background_color", "no-bg");
        RequestBody.put("field_description[0][value]", "");
        RequestBody.put("field_byline[0][value]", "");
        RequestBody.put("field_align", "_none");
        RequestBody.put("field_media[media_library_selection]", "");
        RequestBody.put("field_file_or_link", "file");
        RequestBody.put("field_file[media_library_selection]", "");
        RequestBody.put("field_button_style", "btn btn-secondary");
        RequestBody.put("field_file_button[0][value]", "View report");
        RequestBody.put("field_links[0][title]", "");
        RequestBody.put("field_links[0][uri]", "");
        RequestBody.put("field_links[0][options][attributes][class]", "");
        RequestBody.put("field_links[0][_weight]", "0");
        RequestBody.put("field_links[1][title]", "");
        RequestBody.put("field_links[1][uri]", "");
        RequestBody.put("field_links[1][options][attributes][class]", "");
        RequestBody.put("field_links[1][_weight]", "1");
        RequestBody.put("field_publication", "101");
        RequestBody.put("field_event_category", "_none");
        RequestBody.put("field_countries[0][target_id]", "");
        RequestBody.put("field_countries[0][_weight]", "0");
        RequestBody.put("field_tags[0][target_id]", "");
        RequestBody.put("field_tags[0][_weight]", "0");
        RequestBody.put("field_categories_list", "3");
        RequestBody.put("field_related_publication[0][target_id]", "");
        RequestBody.put("field_related_publication[0][_weight]", "0");
        RequestBody.put("field_long_title[0][value]", "");
        RequestBody.put("field_docid[0][value]", "");
        RequestBody.put("field_relationid[0][value]", "");
        RequestBody.put("field_cid[0][value]", "");
        RequestBody.put("field_scid[0][value]", "");
        RequestBody.put("field_tid[0][value]", "");
        RequestBody.put("field_permalink[0][value]", "");
        RequestBody.put("field_reference[0][value]", "");
        RequestBody.put("field_coa[0][target_id]", "");
        RequestBody.put("field_coa[0][_weight]", "0");
        RequestBody.put("field_coi[0][target_id]", "");
        RequestBody.put("field_coi[0][_weight]", "0");
        RequestBody.put("field_cot[0][target_id]", "");
        RequestBody.put("field_cot[0][_weight]", "0");
        RequestBody.put("field_keywords[0][target_id]", "");
        RequestBody.put("field_keywords[0][_weight]", "0");
        RequestBody.put("field_categories[0][target_id]", "");
        RequestBody.put("field_commentsint[0][value]", "");
        RequestBody.put("field_commentsext[0][value]", "");
        RequestBody.put("field_day[0][value]", "");
        RequestBody.put("field_year[0][value]", "");
        RequestBody.put("field_month[0][value]", "");
        RequestBody.put("field_texis_tags[0][value]", "");
        RequestBody.put("group_tabs[group_tabs__active_tab]", "edit-group-related-info");
        RequestBody.put("field_yoast_seo[0][yoast_seo][focus_keyword]", "");
        RequestBody.put("field_yoast_seo[0][yoast_seo][status]", "0");
        RequestBody.put("field_boosted_keyword[0][value]", "");
        RequestBody.put("field_boosted_keyword[0][boost]", "1");
        RequestBody.put("revision_log[0][value]", "");
        RequestBody.put("created[0][value][date]", "2023-09-12");
        RequestBody.put("created[0][value][time]", "22:06:10");
        RequestBody.put("publish_on[0][value][date]", "");
        RequestBody.put("publish_on[0][value][time]", "");
        RequestBody.put("unpublish_on[0][value][date]", "");
        RequestBody.put("unpublish_on[0][value][time]", "");
        RequestBody.put("field_meta_tags[0][basic][title]", "[node:title] | [site:name]");
        RequestBody.put("field_meta_tags[0][basic][description]", "[node:field_brief]");
        RequestBody.put("field_meta_tags[0][basic][abstract]", "[node:field_brief]");
        RequestBody.put("field_meta_tags[0][basic][keywords]", "");
        RequestBody.put("simple_sitemap[default][index]", "1");
        RequestBody.put("simple_sitemap[default][priority]", "0.5");
        RequestBody.put("simple_sitemap[default][changefreq]", "");
        RequestBody.put("simple_sitemap[default][include_images]", "0");
        RequestBody.put("path[0][pathauto]", "1");
        RequestBody.put("moderation_state[0][state]", "draft");
        RequestBody.put("op", "Save");
        APICaller.ContentCreationAPI(CR.GetRun_ENV()+ CR.GetCreatePublicationPath(),RequestBody,CR.Getcookie());

    }
    @Test(priority = 1,groups = { "Sanity" })
    public void Check_User_Ability_To_Create_News() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();


        RequestBody.put("changed", "1695558430");
        RequestBody.put("langcode[0][value]", "en");
        RequestBody.put("form_build_id", APICaller.PrepareFormData(CR.GetRun_ENV()+ CR.GetCreateNewsPath(),"input[name=form_build_id]"));
        RequestBody.put("form_token", APICaller.PrepareFormData(CR.GetRun_ENV()+ CR.GetCreateNewsPath(),"input[name=form_token]"));

        RequestBody.put("form_id", "node_news_form");
        RequestBody.put("ajax_responsive_preview", "");
        RequestBody.put("title[0][value]",  TestBases.generateRandomString(9));
        RequestBody.put("body[0][value]",  TestBases.generateRandomString(8));
        RequestBody.put("body[0][format]", "full_html");
        RequestBody.put("field_brief[0][value]", "");
        RequestBody.put("field_layout_style", "without-media-news");
        RequestBody.put("field_background_color", "white-smoke-bg");
        RequestBody.put("field_description[0][value]", "");
        RequestBody.put("field_align", "_none");
        RequestBody.put("field_media[media_library_selection]", "");
        RequestBody.put("field_byline[0][value]", "");
        RequestBody.put("field_links[0][title]", "");
        RequestBody.put("field_links[0][uri]", "");
        RequestBody.put("field_links[0][options][attributes][class]", "");
        RequestBody.put("field_links[0][_weight]", "0");
        RequestBody.put("field_links[1][title]", "");
        RequestBody.put("field_links[1][uri]", "");
        RequestBody.put("field_links[1][options][attributes][class]", "");
        RequestBody.put("field_links[1][_weight]", "1");
        RequestBody.put("field_categories", "1140");
        RequestBody.put("field_publication", "_none");
        RequestBody.put("field_briefing_notes_byline[0][value]", "This is a summary of what was said by UNHCR spokesperson – to whom quoted text may be attributed – at today's press briefing at the Palais des Nations in Geneva.");
        RequestBody.put("field_countries[0][target_id]", "");
        RequestBody.put("field_countries[0][_weight]", "0");
        RequestBody.put("field_tags[0][target_id]", "");
        RequestBody.put("field_tags[0][_weight]", "0");
        RequestBody.put("field_categories_list", "4");
        RequestBody.put("field_related_news[0][target_id]", "");
        RequestBody.put("field_related_news[0][_weight]", "0");
        RequestBody.put("field_docid[0][value]", "");
        RequestBody.put("field_relationid[0][value]", "");
        RequestBody.put("field_cid[0][value]", "");
        RequestBody.put("field_pagerelationid[0][value]", "");
        RequestBody.put("field_scid[0][value]", "");
        RequestBody.put("field_tid[0][value]", "");
        RequestBody.put("field_permalink[0][value]", "");
        RequestBody.put("field_reference[0][value]", "");
        RequestBody.put("field_coa[0][target_id]", "");
        RequestBody.put("field_coa[0][_weight]", "0");
        RequestBody.put("field_coi[0][target_id]", "");
        RequestBody.put("field_coi[0][_weight]", "0");
        RequestBody.put("field_cot[0][target_id]", "");
        RequestBody.put("field_cot[0][_weight]", "0");
        RequestBody.put("field_keywords[0][target_id]", "");
        RequestBody.put("field_keywords[0][_weight]", "0");
        RequestBody.put("field_commentsint[0][value]", "");
        RequestBody.put("field_texis_tags[0][value]", "");
        RequestBody.put("group_tabs[group_tabs__active_tab]", "edit-group-related-info");
        RequestBody.put("field_yoast_seo[0][yoast_seo][focus_keyword]", "");
        RequestBody.put("field_yoast_seo[0][yoast_seo][status]", "0");
        RequestBody.put("revision_log[0][value]", "");
        RequestBody.put("created[0][value][date]", "2023-09-24");
        RequestBody.put("created[0][value][time]", "14:27:10");
        RequestBody.put("publish_on[0][value][date]", "");
        RequestBody.put("publish_on[0][value][time]", "");
        RequestBody.put("unpublish_on[0][value][date]", "");
        RequestBody.put("unpublish_on[0][value][time]", "");
        RequestBody.put("field_meta_tags[0][basic][title]", "[node:title] | [site:name]");
        RequestBody.put("field_meta_tags[0][basic][description]", "[node:field_brief]");
        RequestBody.put("field_meta_tags[0][basic][abstract]", "[node:field_brief]");
        RequestBody.put("field_meta_tags[0][basic][keywords]", "");
        RequestBody.put("field_meta_tags[0][advanced][geo_placename]", "");
        RequestBody.put("field_meta_tags[0][advanced][geo_position]", "");
        RequestBody.put("field_meta_tags[0][advanced][geo_region]", "");
        RequestBody.put("field_meta_tags[0][advanced][icbm]", "");
        RequestBody.put("field_meta_tags[0][advanced][canonical_url]", "[node:canonical-domain]");
        RequestBody.put("field_meta_tags[0][advanced][content_language]", "");
        RequestBody.put("field_meta_tags[0][advanced][shortlink]", "[current-page:url:unaliased]");
        RequestBody.put("field_meta_tags[0][advanced][news_keywords]", "");
        RequestBody.put("field_meta_tags[0][advanced][next]", "");
        RequestBody.put("field_meta_tags[0][advanced][prev]", "");
        RequestBody.put("field_meta_tags[0][advanced][standout]", "");
        RequestBody.put("field_meta_tags[0][advanced][generator]", "Varbase");
        RequestBody.put("field_meta_tags[0][advanced][image_src]", "");
        RequestBody.put("field_meta_tags[0][advanced][original_source]", "");
        RequestBody.put("field_meta_tags[0][advanced][rating]", "");
        RequestBody.put("field_meta_tags[0][advanced][referrer]", "origin");
        RequestBody.put("field_meta_tags[0][advanced][refresh]", "");
        RequestBody.put("field_meta_tags[0][advanced][rights]", "©[current-date:html_year] [site:name]. All rights reserved.");
        RequestBody.put("field_meta_tags[0][advanced][set_cookie]", "");
        RequestBody.put("field_meta_tags[0][advanced][revisit_after]", "");
        RequestBody.put("field_meta_tags[0][advanced][cache_control]", "");
        RequestBody.put("field_meta_tags[0][advanced][hreflang_external]", "[node:remote_hreflangs]");
        RequestBody.put("field_meta_tags[0][advanced][expires]", "");
        RequestBody.put("field_meta_tags[0][advanced][pragma]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_determiner]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_site_name]", "[site:name]");
        RequestBody.put("field_meta_tags[0][open_graph][og_type]", "website");
        RequestBody.put("field_meta_tags[0][open_graph][og_url]", "[current-page:url:absolute]");
        RequestBody.put("field_meta_tags[0][open_graph][og_title]", "[current-page:title] | [site:name]");
        RequestBody.put("field_meta_tags[0][open_graph][og_description]", "[node:field_brief]");
        RequestBody.put("field_meta_tags[0][open_graph][og_image]", "[node:share_smart_image]");
        RequestBody.put("field_meta_tags[0][open_graph][og_video]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_image_url]", "[node:share_smart_image]");
        RequestBody.put("field_meta_tags[0][open_graph][og_image_secure_url]", "[node:share_smart_image]");
        RequestBody.put("field_meta_tags[0][open_graph][og_video_secure_url]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_image_type]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_video_type]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_image_width]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_video_width]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_image_height]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_video_height]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_image_alt]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_updated_time]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_video_duration]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_latitude]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_longitude]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_see_also]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_street_address]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_locality]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_region]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_postal_code]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_country_name]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_email]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_phone_number]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_fax_number]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_locale]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_locale_alternative]", "");
        RequestBody.put("field_meta_tags[0][open_graph][article_author]", "");
        RequestBody.put("field_meta_tags[0][open_graph][article_publisher]", "");
        RequestBody.put("field_meta_tags[0][open_graph][article_section]", "");
        RequestBody.put("field_meta_tags[0][open_graph][article_tag]", "");
        RequestBody.put("field_meta_tags[0][open_graph][article_published_time]", "");
        RequestBody.put("field_meta_tags[0][open_graph][article_modified_time]", "");
        RequestBody.put("field_meta_tags[0][open_graph][article_expiration_time]", "");
        RequestBody.put("field_meta_tags[0][open_graph][book_author]", "");
        RequestBody.put("field_meta_tags[0][open_graph][book_isbn]", "");
        RequestBody.put("field_meta_tags[0][open_graph][book_release_date]", "");
        RequestBody.put("field_meta_tags[0][open_graph][book_tag]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_audio]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_audio_secure_url]", "");
        RequestBody.put("field_meta_tags[0][open_graph][og_audio_type]", "");
        RequestBody.put("field_meta_tags[0][open_graph][profile_first_name]", "");
        RequestBody.put("field_meta_tags[0][open_graph][profile_last_name]", "");
        RequestBody.put("field_meta_tags[0][open_graph][profile_gender]", "");
        RequestBody.put("field_meta_tags[0][open_graph][profile_username]", "");
        RequestBody.put("field_meta_tags[0][open_graph][video_actor]", "");
        RequestBody.put("field_meta_tags[0][open_graph][video_actor_role]", "");
        RequestBody.put("field_meta_tags[0][open_graph][video_director]", "");
        RequestBody.put("field_meta_tags[0][open_graph][video_release_date]", "");
        RequestBody.put("field_meta_tags[0][open_graph][video_series]", "");
        RequestBody.put("field_meta_tags[0][open_graph][video_tag]", "");
        RequestBody.put("field_meta_tags[0][open_graph][video_writer]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_type]", "summary_large_image");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_description]", "[node:field_brief]");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_site]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_title]", "[current-page:title] | [site:name]");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_site_id]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_creator]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_creator_id]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_donottrack]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_page_url]", "[current-page:url:absolute]");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_image]", "[node:share_smart_image]");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_image_alt]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_image_height]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_image_width]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_gallery_image0]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_gallery_image1]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_gallery_image2]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_gallery_image3]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_app_store_country]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_app_name_iphone]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_app_id_iphone]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_app_url_iphone]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_app_name_ipad]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_app_id_ipad]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_app_url_ipad]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_app_name_googleplay]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_app_id_googleplay]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_app_url_googleplay]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_player]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_player_width]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_player_height]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_player_stream]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_player_stream_content_type]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_label1]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_data1]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_label2]", "");
        RequestBody.put("field_meta_tags[0][twitter_cards][twitter_cards_data2]", "");
        RequestBody.put("field_meta_tags[0][schema_article][schema_article_type]", "NewsArticle");
        RequestBody.put("field_meta_tags[0][schema_article][schema_article_headline]", "[node:title]");
        RequestBody.put("field_meta_tags[0][schema_article][schema_article_name]", "[node:title]");
        RequestBody.put("field_meta_tags[0][schema_article][schema_article_about]", "[node:field_topic:0:entity:name]");
        RequestBody.put("field_meta_tags[0][schema_article][schema_article_description]", "[node:field_brief]");
        RequestBody.put("field_meta_tags[0][schema_article][schema_article_image][@type]", "ImageObject");
        RequestBody.put("field_meta_tags[0][schema_article][schema_article_image][representativeOfPage]", "True");
        RequestBody.put("field_meta_tags[0][schema_article][schema_article_image][url]", "[node:share_smart_image]");
        RequestBody.put("field_meta_tags[0][schema_article][schema_article_image][width]", "");
        RequestBody.put("field_meta_tags[0][schema_article][schema_article_image][height]", "");
        RequestBody.put("field_meta_tags[0][schema_article][schema_article_date_published]", "[node:created:html_datetime]");
        RequestBody.put("field_meta_tags[0][schema_article][schema_article_date_modified]", "[node:changed:html_datetime]");

        RequestBody.put("simple_sitemap[default][index]", "1");
        RequestBody.put("simple_sitemap[default][priority]", "0.5");
        RequestBody.put("simple_sitemap[default][changefreq]", "");
        RequestBody.put("simple_sitemap[default][include_images]", "0");
        RequestBody.put("path[0][pathauto]", "1");
        RequestBody.put("moderation_state[0][state]", "draft");
        RequestBody.put("op", "Save");
        APICaller.ContentCreationAPI(CR.GetRun_ENV()+ CR.GetCreateNewsPath(),RequestBody,CR.Getcookie());

    }
    @Test(priority = 3,groups = { "Sanity" })
    public void Check_User_Ability_To_Create_Event() throws Exception {
        Map<String, String> RequestBody = new LinkedHashMap<>();

        RequestBody.put("changed", "1695558430");
        RequestBody.put("langcode[0][value]", "en");
        // to be completed by hamza/ibrahim
    }
 }

