package com.ooyala.playback.url;

import javax.xml.bind.annotation.*;

/**
 * Created by jitendra on 30/11/16.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "value" })
public class EnableSSL {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "name")
    protected String name;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}