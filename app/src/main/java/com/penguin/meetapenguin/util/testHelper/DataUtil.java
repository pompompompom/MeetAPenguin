package com.penguin.meetapenguin.util.testHelper;

import android.content.Context;
import android.content.SharedPreferences;

import com.penguin.meetapenguin.MeetAPenguim;
import com.penguin.meetapenguin.dblayout.ContactController;
import com.penguin.meetapenguin.dblayout.InboxMessageController;
import com.penguin.meetapenguin.entities.Attribute;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.entities.ContactInfo;
import com.penguin.meetapenguin.entities.InboxMessage;
import com.penguin.meetapenguin.util.entitiesHelper.AttributesHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Data Utilities for creating fake data. It is used for test purpose.
 */
public class DataUtil {

    private static final ContactInfo FACEBOOK_INFO =
            new ContactInfo(AttributesHelper.getAttribute(AttributesHelper.AttributeType.Facebook), "", "www.facebook.com/teste");

    private static final ContactInfo LOCATION_INFO =
            new ContactInfo(AttributesHelper.getAttribute(AttributesHelper.AttributeType.Location), "", "Cupertino - CA");

    private static final Set<ContactInfo> DEFAULT_CONTACT_INFO_LIST = new LinkedHashSet<>();

    private static final String CREATE_FAKE_DATA = "fakedata";
    private static final String TAG = DataUtil.class.getSimpleName();

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
        contact1.setId(123123);
        contact1.setName("John John");
        contact1.setDescription("Student");
        contact1.setPhotoUrl("http://www.billybobproducts.com/sc_images/products/582_large_image.png");
        contact1.setContactInfoArrayList(DEFAULT_CONTACT_INFO_LIST);
        contact1.setExpiration(new Date().getTime() + TimeUnit.DAYS.toMillis(7));
        dummyData.add(contact1);

        Contact contact2 = new Contact();
        contact2.setName("Stuart Little");
        contact2.setDescription("Rat");
        contact1.setId(9878742);
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

    public static Contact mockContact() {
        Contact c = new Contact();

        c.setExpiration(new Date(1464588270000L).getTime());
        c.setName("Prin");
        c.setDescription("CMU Student");
        c.setPhotoUrl("photoURL");
        c.setId(30);

        Set<ContactInfo> al = new LinkedHashSet<ContactInfo>();

        Attribute a1 = new Attribute();
        a1.setName("Facebook");
        a1.setId(4);
        ContactInfo c1 = new ContactInfo(a1, "", "Prin FB page");

        ContactInfo c2 = new ContactInfo();
        Attribute a2 = new Attribute();
        a2.setName("Location");
        a2.setId(10);
        c2.setAttributeValue("Pittsburgh");
        c2.setAttribute(a2);

        ContactInfo c3 = new ContactInfo();
        Attribute a3 = new Attribute();
        a3.setName("Email");
        a3.setId(2);
        c3.setAttributeValue("prin@email.com");
        c3.setAttribute(a3);

        al.add(c1);
        al.add(c2);
        al.add(c3);

        c.setContactInfoArrayList(al);

        return c;
    }

    public static Contact mockContact1() {
        Contact c = new Contact();

        c.setExpiration(new Date(1464588270000L).getTime());
        c.setName("urbano");
        c.setDescription("CMU-SV Student");
        c.setPhotoUrl("2130837579");
        c.setId(31);

        Set<ContactInfo> al = new LinkedHashSet<ContactInfo>();
        ContactInfo c1 = new ContactInfo();
        Attribute a1 = new Attribute();
        a1.setName("Facebook");
        a1.setId(4);
        c1.setAttributeValue("Urbanos FB page");
        c1.setAttribute(a1);

        ContactInfo c2 = new ContactInfo();
        Attribute a2 = new Attribute();
        a2.setName("Email");
        a2.setId(2);
        c2.setAttributeValue("oximer@gmail.com");
        c2.setAttribute(a2);

        ContactInfo c3 = new ContactInfo();
        Attribute a3 = new Attribute();
        a3.setName("Twitter");
        a3.setId(5);
        c3.setAttributeValue("@oximer");
        c3.setAttribute(a3);

        ContactInfo c4 = new ContactInfo();
        Attribute a4 = new Attribute();
        a4.setName("Website");
        a4.setId(9);
        c4.setAttributeValue("www.oximer.com");
        c4.setAttribute(a4);

        al.add(c1);
        al.add(c2);
        al.add(c3);
        al.add(c4);

        c.setContactInfoArrayList(al);

        return c;
    }

    public static Contact mockContact2() {
        Contact c = new Contact();

        c.setExpiration(new Date(1464588270000L).getTime());
        c.setName("Mita");
        c.setDescription("Google Employee");
        c.setPhotoUrl("mita photo");
        c.setId(32);

        Set<ContactInfo> al = new LinkedHashSet<ContactInfo>();
        ContactInfo c1 = new ContactInfo();
        Attribute a1 = new Attribute();
        a1.setName("Facebook");
        a1.setId(4);
        c1.setAttributeValue("Mita FB page");
        c1.setAttribute(a1);

        ContactInfo c2 = new ContactInfo();
        Attribute a2 = new Attribute();
        a2.setName("Phone Number");
        a2.setId(3);
        c2.setAttributeValue("555-0123");
        c2.setAttribute(a2);

        ContactInfo c3 = new ContactInfo();
        Attribute a3 = new Attribute();
        a3.setName("Birthday");
        a3.setId(2);
        c3.setAttributeValue("01/01/92");
        c3.setAttribute(a3);

        al.add(c1);
        al.add(c2);
        al.add(c3);

        c.setContactInfoArrayList(al);

        return c;
    }

    public static void createFakeDataForInboxMessage() {

        SharedPreferences sharedPref = MeetAPenguim.getAppContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        boolean alreadyCreated = sharedPref.getBoolean(CREATE_FAKE_DATA, false);
        if (alreadyCreated)
            return;

        //Adding first face message
        InboxMessage inboxMessage1 = new InboxMessage();
        Contact contact1 = DataUtil.mockContact();
        contact1.setContactInfoArrayList(new LinkedHashSet<ContactInfo>() {
        });
        contact1.setExpiration(new Date().getTime());
        contact1.setPhotoUrl("http://www.billybobproducts.com/sc_images/products/582_large_image.png");

        ContactController contactController = new ContactController(MeetAPenguim.getAppContext());
//        contactController.create(contact1);

        inboxMessage1.setId(1);
        inboxMessage1.setCloudId(1);
        inboxMessage1.setContact(contact1);
        inboxMessage1.setMessage("Email for this contact has expired.");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date fakeDate = cal.getTime();
        inboxMessage1.setTimeStamp(fakeDate.getTime());

        InboxMessageController inboxMessageController = new InboxMessageController(MeetAPenguim.getAppContext());
        inboxMessageController.create(inboxMessage1);

        //Adding second face message
        InboxMessage inboxMessage2 = new InboxMessage();
        Contact contact8 = new Contact();
        contact8.setName("Wozniak");
        contact8.setDescription("Engineer");
        contact8.setId(2);
        contact8.setExpiration(new Date().getTime());
        contact8.setContactInfoArrayList(new LinkedHashSet<ContactInfo>());
        contact8.setPhotoUrl("http://www.landsnail.com/apple/local/profile/New_Folder/graphics/wozniak.gif");
//        contactController.create(contact8);
        inboxMessage2.setContact(contact8);
        inboxMessage2.setId(2);
        inboxMessage1.setCloudId(2);
        inboxMessage2.setMessage("Contact is requesting Email update.");

        cal.add(Calendar.MONTH, -3);
        Date fakeDate2 = cal.getTime();

        inboxMessage2.setTimeStamp(fakeDate2.getTime());
        inboxMessageController.create(inboxMessage2);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(CREATE_FAKE_DATA, true);
        editor.commit();
    }
}
