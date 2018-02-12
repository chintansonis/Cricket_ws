package com.cricket.cricketchallenge.commonmodel;

/**
 * The type Db contacts model.
 */
public class DbContactsModel
{


    private int contactId;
    private String userName;
    private String emails;
    private String phone;
    private int status;




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
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * Gets emails.
     *
     * @return the emails
     */
    public String getEmails()
    {
        return emails;
    }

    /**
     * Sets emails.
     *
     * @param emails the emails
     */
    public void setEmails(String emails)
    {
        this.emails = emails;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(int status)
    {
        this.status = status;
    }
}
