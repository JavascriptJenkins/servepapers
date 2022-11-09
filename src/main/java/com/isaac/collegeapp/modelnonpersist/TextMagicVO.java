package com.isaac.collegeapp.modelnonpersist;


// https://rest.textmagic.com/api/v2/doc/#
public class TextMagicVO {

    public String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String phones;

//    {
//        "text": "string",
//            "templateId": "string",
//            "sendingTime": "string",
//            "sendingDateTime": "string",
//            "sendingTimezone": "string",
//            "contacts": "string",
//            "lists": "string",
//            "phones": "string",
//            "cutExtra": "string",
//            "partsCount": "string",
//            "referenceId": "string",
//            "from": "string",
//            "rrule": "string",
//            "createChat": "string",
//            "tts": "string",
//            "local": "string",
//            "localCountry": "string",
//            "destination": "string",
//            "resources": "string"
//    }

}
