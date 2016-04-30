package com.penguin.meetapenguin.util;

import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.entities.ContactInfo;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Data Utilities
 */
public class DataUtil {

    private static final ContactInfo FACEBOOK_INFO =
            new ContactInfo(AttributesHelper.getAttribute(AttributesHelper.AttributeType.Facebook), "", "www.facebook.com/teste");

    private static final ContactInfo LOCATION_INFO =
            new ContactInfo(AttributesHelper.getAttribute(AttributesHelper.AttributeType.Location), "", "Cupertino - CA");

    private static final Set<ContactInfo> DEFAULT_CONTACT_INFO_LIST = new LinkedHashSet<>();

    static {
        DEFAULT_CONTACT_INFO_LIST.add(FACEBOOK_INFO);
        DEFAULT_CONTACT_INFO_LIST.add(LOCATION_INFO);
    }

    /**
     * Makes a fake contact for testing purposes.
     *
     * @return Contact.
     */
    public static Contact getMockContact() {
        Contact contact = new Contact();
        contact.setName("Tom Brady");
        contact.setDescription("Player");
        contact.setPhotoUrl("http://a.espncdn.com/combiner/i?img=/i/headshots/nfl/players/full/2330.png&w=350&h=254");
        contact.setContactInfoArrayList(DEFAULT_CONTACT_INFO_LIST);
        return contact;
    }

    public static ArrayList<Contact> createFakeData() {
        ArrayList dummyData = new ArrayList<>();
        Contact contact1 = new Contact();
        contact1.setName("John John");
        contact1.setDescription("Student");
        contact1.setPhotoUrl("http://www.billybobproducts.com/sc_images/products/582_large_image.png");
        contact1.setContactInfoArrayList(DEFAULT_CONTACT_INFO_LIST);
        dummyData.add(contact1);

        Contact contact2 = new Contact();
        contact2.setName("Stuart Little");
        contact2.setDescription("Rat");
        contact2.setPhotoUrl("http://www.thehindu.com/thehindu/yw/2002/10/26/images/2002102600050201.jpg");
        contact2.setContactInfoArrayList(DEFAULT_CONTACT_INFO_LIST);
        dummyData.add(contact2);

        Contact contact3 = new Contact();
        contact3.setName("Tony Stark");
        contact3.setDescription("Super Hero");
        contact3.setPhotoUrl("http://vignette4.wikia.nocookie.net/marvelmovies/images/9/9a/Iron-man-site-tony-stark.jpg/revision/latest?cb=20120416023223");
        contact3.setContactInfoArrayList(DEFAULT_CONTACT_INFO_LIST);
        dummyData.add(contact3);

        Contact contact4 = new Contact();
        contact4.setName("Zumbi");
        contact4.setDescription("Walking Dead");
        contact4.setPhotoUrl("http://geraligado.blog.br/wp-content/uploads/2013/01/As-melhores-maquiagens-de-zumbi-11.jpeg");
        contact4.setContactInfoArrayList(DEFAULT_CONTACT_INFO_LIST);
        dummyData.add(contact4);

        Contact contact5 = new Contact();
        contact5.setName("Bill Gates");
        contact5.setDescription("Billionare");
        contact5.setPhotoUrl("https://static-secure.guim.co.uk/sys-images/Medaia/Pix/pictures/2011/3/1/1298983226609/Bill-Gates-007.jpg");
        contact5.setContactInfoArrayList(DEFAULT_CONTACT_INFO_LIST);
        dummyData.add(contact5);

        Contact contact6 = new Contact();
        contact6.setName("Alcapone");
        contact6.setDescription("Mafia");
        contact6.setPhotoUrl("http://www.babyfacenelsonjournal.com/uploads/3/8/2/4/3824310/5721541_orig.jpg");
        contact6.setContactInfoArrayList(DEFAULT_CONTACT_INFO_LIST);
        dummyData.add(contact6);

        Contact contact7 = new Contact();
        contact7.setName("Allan Grespan");
        contact7.setDescription("Economist");
        contact7.setPhotoUrl("http://www.washingtonspeakers.com/images/photos/sp1/2300.jpg");
        contact7.setContactInfoArrayList(DEFAULT_CONTACT_INFO_LIST);
        dummyData.add(contact7);


        Contact contact8 = new Contact();
        contact8.setName("Wozniak");
        contact8.setDescription("Engineer");
        contact8.setPhotoUrl("http://www.landsnail.com/apple/local/profile/New_Folder/graphics/wozniak.gif");
        contact8.setContactInfoArrayList(DEFAULT_CONTACT_INFO_LIST);
        dummyData.add(contact8);


        Contact contact9 = new Contact();
        contact9.setName("Tony Blair");
        contact9.setDescription("Prime Minister");
        contact9.setPhotoUrl("https://pbs.twimg.com/profile_images/1675579492/Tony_Blair_pic.JPG");
        contact9.setContactInfoArrayList(DEFAULT_CONTACT_INFO_LIST);
        dummyData.add(contact9);


        Contact contact10 = new Contact();
        contact10.setName("Jude Law");
        contact10.setDescription("Actor");
        contact10.setPhotoUrl("http://a1.files.biography.com/image/upload/c_fill,cs_srgb,dpr_1.0,g_face,h_300,q_80,w_300/MTE1ODA0OTcxNjkzODY4NTU3.jpg");
        contact10.setContactInfoArrayList(DEFAULT_CONTACT_INFO_LIST);
        dummyData.add(contact10);

        return dummyData;
    }
}
