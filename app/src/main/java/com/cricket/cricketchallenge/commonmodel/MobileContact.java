package com.cricket.cricketchallenge.commonmodel;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * The type Mobile contact.
 */
public class MobileContact implements Serializable, Comparable<MobileContact>
{
    private int contactId;

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    private String name;
    private String contact;
    private boolean isChecked = false;

    /**
     * Instantiates a new Mobile contact.
     *
     * @param name    the name
     * @param contact the contact
     */
    public MobileContact(int contactId, String name, String contact)
    {
        this.contactId = contactId;
        this.name = name;
        this.contact = contact;
    }

    /**
     * Is checked boolean.
     *
     * @return the boolean
     */
    public boolean isChecked()
    {
        return isChecked;
    }

    /**
     * Sets is checked.
     *
     * @param isChecked the is checked
     */
    public void setIsChecked(boolean isChecked)
    {
        this.isChecked = isChecked;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets contact.
     *
     * @return the contact
     */
    public String getContact()
    {
        return contact;
    }

    /**
     * Sets contact.
     *
     * @param contact the contact
     */
    public void setContact(String contact)
    {
        this.contact = contact;
    }


    // Overriding the compareTo method
    @Override
    public int compareTo(@NonNull MobileContact mobileContact)
    {
        return (this.name).compareTo(mobileContact.name);
    }

    @Override
    public boolean equals(Object o)
    {
        return super.equals(o);
    }
}
