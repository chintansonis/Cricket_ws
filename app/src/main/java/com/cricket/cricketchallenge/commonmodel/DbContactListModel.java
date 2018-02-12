package com.cricket.cricketchallenge.commonmodel;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Db contact list model.
 */
public class DbContactListModel
{

    private int contactId;
    private String name;
    private Set<String> email = new HashSet<>();
    private Set<String> phoneNumber = new HashSet<>();

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContactId()
    {
        return contactId;
    }

    /**
     * Sets contact id.
     *
     * @param contactId the contact id
     */
    public void setContactId(int contactId)
    {
        this.contactId = contactId;
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
     * Gets email.
     *
     * @return the email
     */
    public Set<String> getEmail()
    {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(Set<String> email)
    {
        this.email = email;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public Set<String> getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(Set<String> phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
}
