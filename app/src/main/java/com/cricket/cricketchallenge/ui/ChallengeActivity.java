package com.cricket.cricketchallenge.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.applidium.headerlistview.HeaderListView;
import com.applidium.headerlistview.SectionAdapter;
import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.api.RestClient;
import com.cricket.cricketchallenge.api.responsepojos.ResponseAppUser;
import com.cricket.cricketchallenge.api.responsepojos.UserModel;
import com.cricket.cricketchallenge.commonmodel.DbContactsModel;
import com.cricket.cricketchallenge.commonmodel.MobileContact;
import com.cricket.cricketchallenge.custom.MyEditText;
import com.cricket.cricketchallenge.custom.TfTextView;
import com.cricket.cricketchallenge.database.TableContacts;
import com.cricket.cricketchallenge.helper.AppConstants;
import com.cricket.cricketchallenge.helper.Functions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chintans on 15-12-2017.
 */
public class ChallengeActivity extends BaseActivity {
    private Toolbar toolbar;
    private TfTextView txtTitle;
    private com.cricket.cricketchallenge.custom.MyEditText etsearchaddinvite;
    private android.widget.ImageView ivSearch;
    private android.widget.LinearLayout lllistaddinvite;
    private android.widget.LinearLayout lvMyContacts;
    public ArrayList<UserModel> contactPojoArrayList = new ArrayList<>();
    private HeaderListView headerlist;
    private List<MobileContact> mobileContacts;
    private LayoutInflater mInflater;
    private TfTextView tvnoprev;
    private TfTextView tvchallnege;
    private LinearLayout llcontacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        updateContactArrayList();
        init();
        if (Functions.isConnected(ChallengeActivity.this)) {
            getAppUserListApi();
        } else {
            Functions.showToast(ChallengeActivity.this, getResources().getString(R.string.err_no_internet_connection));
        }
    }

    private void getAppUserListApi() {
        showProgressDialog(false);
        RestClient.get().getAppUsersList().enqueue(new Callback<List<ResponseAppUser>>() {
            @Override
            public void onResponse(Call<List<ResponseAppUser>> call, Response<List<ResponseAppUser>> response) {
                hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().get(0).getStatus() == AppConstants.ResponseSuccess) {
                        contactPojoArrayList.addAll(response.body().get(0).getData());
                        setData(false);
                    } else {
                        Functions.showToast(ChallengeActivity.this, response.body().get(0).getMessage());
                    }
                } else {
                    Functions.showToast(ChallengeActivity.this, getString(R.string.err_something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<List<ResponseAppUser>> call, Throwable t) {
                hideProgressDialog();
                Functions.showToast(ChallengeActivity.this, getString(R.string.err_something_went_wrong));
            }
        });
    }

    private void updateContactArrayList() {
        TableContacts tbContacts = new TableContacts(ChallengeActivity.this);
        List<DbContactsModel> dbContactsModels = tbContacts.getAllContacts();
        mobileContacts = new ArrayList<>();
        mobileContacts.clear();
        for (int i = 0; i < dbContactsModels.size(); i++) {
            Log.d("System out", "Contact id from update arraylist" + dbContactsModels.get(i).getContactId());
            mobileContacts.addAll(getMobileContacts(0, dbContactsModels.get(i)));
        }
    }

    private List<MobileContact> getMobileContacts(int contactType, DbContactsModel dbContactsModel) {
        List<MobileContact> tempMobileContacts = new ArrayList<>();
        String[] strings;
        if (contactType == 0) {
            strings = !dbContactsModel.getPhone().isEmpty() ? dbContactsModel.getPhone().split(",") : new String[0];
        } else {
            strings = !dbContactsModel.getEmails().isEmpty() ? dbContactsModel.getEmails().split(",") : new String[0];
        }
        for (String contact : strings) {
            tempMobileContacts.add(new MobileContact(dbContactsModel.getContactId(), dbContactsModel.getUserName(), contact));
        }
        return tempMobileContacts;
    }

    private void init() {
        llcontacts = (LinearLayout) findViewById(R.id.ll_contacts);
        tvchallnege = (TfTextView) findViewById(R.id.tv_challnege);
        tvnoprev = (TfTextView) findViewById(R.id.tv_no_prev);
        lvMyContacts = (LinearLayout) findViewById(R.id.lvMyContacts);
        lllistaddinvite = (LinearLayout) findViewById(R.id.ll_list_add_invite);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        etsearchaddinvite = (MyEditText) findViewById(R.id.et_search_add_invite);
        //loadAd();
        initToolbar();

        etsearchaddinvite.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = etsearchaddinvite.getText().toString();
                filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        tvchallnege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntent(ChallengeActivity.this, ChallengeSentActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void filter(String Searchtext) {
        etsearchaddinvite.setGravity(Gravity.CENTER);
        ivSearch.setVisibility(View.VISIBLE);
        if (Searchtext.length() == 0) {
            Functions.hideKeyPad(ChallengeActivity.this, etsearchaddinvite);
            setData(false);
        } else {
            etsearchaddinvite.setGravity(Gravity.LEFT | Gravity.CENTER);
            ivSearch.setVisibility(View.GONE);
        }
    }

    private void setData(boolean search_check) {
        if (search_check == false) {
            lvMyContacts.setVisibility(View.GONE);
            lllistaddinvite.setVisibility(View.VISIBLE);
            Log.e("System out", "search_check in false " + search_check);
            headerlist = new HeaderListView(this);
            headerlist.setAdapter(new SectionAdapter() {
                @Override
                public int numberOfSections() {
                    return 2;
                }

                @Override
                public int numberOfRows(int section) {
                    switch (section) {
                        case 0:
                            return contactPojoArrayList.size();
                        case 1:
                            return mobileContacts.size();
                        default:
                            break;
                    }
                    return 0;
                }

                @Override
                public Object getRowItem(int section, int row) {
                    return null;
                }

                @Override
                public boolean hasSectionHeaderView(int section) {
                    return true;
                }

                @Override
                public View getRowView(final int section, final int row, View convertView, ViewGroup parent) {
                    View v;
                    ViewHolder viewHolder;
                    if (convertView == null) {
                        mInflater = (LayoutInflater) ChallengeActivity.this
                                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                        v = mInflater.inflate(R.layout.row_item_add_invite, null);
                        viewHolder = new ViewHolder();
                        viewHolder.iv_pic_add_invite = (CircleImageView) v.findViewById(R.id.iv_pic_add_invite);
                        viewHolder.tv_name_add_invite = (TfTextView) v.findViewById(R.id.tv_name_add_invite);
                        viewHolder.llRowDetail = (LinearLayout) v.findViewById(R.id.llRowDetail);
                        viewHolder.tvRowNoPrev = (TfTextView) v.findViewById(R.id.tvRowNoPrev);
                        viewHolder.tv_no_add_invite = (TfTextView) v.findViewById(R.id.tv_no_add_invite);
                        viewHolder.iv_right_icon_add_invite = (ImageView) v.findViewById(R.id.iv_right_icon_add_invite);
                        v.setTag(viewHolder);
                    } else {
                        viewHolder = (ViewHolder) convertView.getTag();
                        v = convertView;
                    }
                    if (section == 0) {
                        if (contactPojoArrayList.size() > 0) {
                            viewHolder.datumContact = contactPojoArrayList.get(row);
                            viewHolder.iv_right_icon_add_invite.setImageResource(viewHolder.datumContact.isChecked() ? R.drawable.right_icon_contact : R.drawable.roun_cheqbox);
                            /*imageloader1.displayImage(Constants.IMAGE_URL + contactPojoArrayList.get(row).getImagePath().replace("~", "").trim(), viewHolder.iv_pic_add_invite, options55);*/
                            viewHolder.tv_name_add_invite.setText(contactPojoArrayList.get(row).getUserFullname());
                            viewHolder.tv_no_add_invite.setText(contactPojoArrayList.get(row).getUserMobile());
                            viewHolder.tv_name_add_invite.setTag(viewHolder.datumContact);
                            viewHolder.llRowDetail.setVisibility(View.VISIBLE);
                            viewHolder.tvRowNoPrev.setVisibility(View.GONE);
                        } else {
                            viewHolder.llRowDetail.setVisibility(View.GONE);
                            viewHolder.tvRowNoPrev.setVisibility(View.VISIBLE);
                        }
                    }
                    if (section == 1) {
                        if (mobileContacts.size() > 0) {
                            viewHolder.mobileContact = mobileContacts.get(row);
                            viewHolder.llRowDetail.setVisibility(View.VISIBLE);
                            viewHolder.tvRowNoPrev.setVisibility(View.GONE);
                            viewHolder.iv_right_icon_add_invite.setImageResource(viewHolder.mobileContact.isChecked() ? R.drawable.right_icon_contact : R.drawable.roun_cheqbox);
                            /*if (liftOffereeArrayList != null) {
                                if (liftOffereeArrayList.size() > 0) {
                                    for (int k = 0; k < liftOffereeArrayList.size(); k++) {
                                        Log.d("System out", "ID FROM LIFT" + liftOffereeArrayList.get(k).getUserID());
                                        if (liftOffereeArrayList.get(k).getContactID() != null && liftOffereeArrayList.get(k).getContactID() != 0) {
                                            Log.d("System out", "selection " + liftOffereeArrayList.get(k).getContactID());
                                            if (String.valueOf(liftOffereeArrayList.get(k).getContactID()).equalsIgnoreCase(String.valueOf(mobileContacts.get(row).getContactId()))) {
                                                viewHolder.llRowDetail.setEnabled(false);
                                            } else {
                                                viewHolder.llRowDetail.setEnabled(true);
                                            }
                                        }
                                    }
                                } else {

                                }
                            }*/
                            viewHolder.tv_name_add_invite.setText(mobileContacts.get(row).getName());
                            Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
                            phoneNumber.setRawInput(mobileContacts.get(row).getContact().trim());
                            viewHolder.tv_no_add_invite.setText(PhoneNumberUtil.getInstance().format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
                            /*imageloader1.displayImage("", viewHolder.iv_pic_add_invite, options55);*/
                            viewHolder.tv_name_add_invite.setTag(viewHolder.mobileContact);
                        } else {
                            viewHolder.llRowDetail.setVisibility(View.GONE);
                            viewHolder.tvRowNoPrev.setVisibility(View.VISIBLE);
                        }
                    }
                    viewHolder.llRowDetail.setTag(viewHolder);
                    viewHolder.llRowDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onViewItemClick(v);
                        }

                        private void onViewItemClick(View v) {
                            ViewHolder holder = (ViewHolder) v.getTag();
                            notifyDataSetChanged();
                            /*if (holder.tv_name_add_invite.getTag() instanceof DatumContact) {
                                if (liftOffereeArrayList != null) {
                                    if (liftOffereeArrayList.size() >= SyncStateContract.Constants.package_user || getSelectedDatumMobileContacts().size() + getSelectedDatumContacts().size() >= Constants.package_user) {
                                        DatumContact datumContact = (DatumContact) holder.tv_name_add_invite.getTag();
                                        if (datumContact.isChecked()) {
                                            datumContact.setChecked(!datumContact.isChecked());
                                        } else {
                                            premiumPopUp();
                                        }
                                    } else {
                                        DatumContact datumContact = (DatumContact) holder.tv_name_add_invite.getTag();
                                        datumContact.setChecked(!datumContact.isChecked());
                                    }
                                } else if (getSelectedDatumMobileContacts().size() + getSelectedDatumContacts().size() >= Constants.package_user) {
                                    DatumContact datumContact = (DatumContact) holder.tv_name_add_invite.getTag();
                                    if (datumContact.isChecked()) {
                                        datumContact.setChecked(!datumContact.isChecked());
                                    } else {
                                        premiumPopUp();
                                    }
                                } else {
                                    DatumContact datumContact = (DatumContact) holder.tv_name_add_invite.getTag();
                                    datumContact.setChecked(!datumContact.isChecked());
                                }
                            } else if (holder.tv_name_add_invite.getTag() instanceof MobileContact) {
                                if (liftOffereeArrayList != null) {
                                    if (liftOffereeArrayList.size() >= Constants.package_user || getSelectedDatumMobileContacts().size() + getSelectedDatumContacts().size() >= Constants.package_user) {
                                        MobileContact datumContact = (MobileContact) holder.tv_name_add_invite.getTag();
                                        if (datumContact.isChecked()) {
                                            datumContact.setChecked(!datumContact.isChecked());
                                        } else {
                                            premiumPopUp();
                                        }
                                    } else {
                                        MobileContact datumContact = (MobileContact) holder.tv_name_add_invite.getTag();
                                        datumContact.setChecked(!datumContact.isChecked());
                                    }
                                } else if (getSelectedDatumMobileContacts().size() + getSelectedDatumContacts().size() >= Constants.package_user) {
                                    MobileContact datumContact = (MobileContact) holder.tv_name_add_invite.getTag();
                                    if (datumContact.isChecked()) {
                                        datumContact.setChecked(!datumContact.isChecked());
                                    } else {
                                        premiumPopUp();
                                    }
                                } else {
                                    MobileContact datumContact = (MobileContact) holder.tv_name_add_invite.getTag();
                                    datumContact.setChecked(!datumContact.isChecked());
                                }
                            }
                            if (getSelectedDatumContacts().size() > 0 || getSelectedDatumMobileContacts().size() > 0 || count != 0) {
                                count = 0;
                                for (int i = 0; i < getSelectedDatumContacts().size(); i++) {
                                    count++;
                                }
                                for (int i = 0; i < getSelectedDatumMobileContacts().size(); i++) {
                                    count++;
                                }
                                tvSendInvite.setEnabled(true);
                                tvSendInvite.setText("Send Invite" + " " + "(" + count + ")");
                                tvSendInvite.setBackgroundResource(R.drawable.create_trip_btn);
                                if (count == 0) {
                                    tvSendInvite.setEnabled(false);
                                    tvSendInvite.setText("Send Invite");
                                    tvSendInvite.setBackgroundResource(R.drawable.create_trip_btn_light);
                                }
                            } else {
                                tvSendInvite.setText("Send Invite");
                                tvSendInvite.setEnabled(false);
                                tvSendInvite.setBackgroundResource(R.drawable.create_trip_btn_light);
                            }
                            notifyDataSetChanged();*/
                        }
                    });
                    return v;
                }

                class ViewHolder {
                    CircleImageView iv_pic_add_invite;
                    TfTextView tv_name_add_invite;
                    TfTextView tv_no_add_invite;
                    LinearLayout llRowDetail;
                    TfTextView tvRowNoPrev;
                    ImageView iv_right_icon_add_invite;
                    UserModel datumContact;
                    MobileContact mobileContact;
                }

                @Override
                public int getSectionHeaderViewTypeCount() {
                    return 2;
                }

                @Override
                public int getSectionHeaderItemViewType(int section) {
                    return section % 2;
                }

                @Override
                public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {

                    if (convertView == null) {
                        LayoutInflater mInflater = (LayoutInflater) ChallengeActivity.this
                                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                        convertView = mInflater.inflate(R.layout.row_header_add_invite, null);
                    }
                    TfTextView tv_header_add_invite = (TfTextView) convertView.findViewById(R.id.tv_header_add_invite);
                    if (section == 0) {
                        if (contactPojoArrayList.size() > 0) {
                            tv_header_add_invite.setText(getString(R.string.ppl_already_using_cricketchalleneg) + " " + "(" + " " + contactPojoArrayList.size() + " " + ")");
                        } else {
                            tv_header_add_invite.setText(getString(R.string.ppl_already_using_cricketchalleneg));
                        }
                    } else if (section == 1) {
                        tv_header_add_invite.setText(getString(R.string.get_them_on_board));
                    }
                    return convertView;
                }

                public void onRowItemClick(AdapterView<?> parent, View view, int section, int row, long id) {
                    super.onRowItemClick(parent, view, section, row, id);
                }
            });
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lllistaddinvite.addView(headerlist);
                }
            });
        }

    }

    private void loadAd() {
        final InterstitialAd interstitialAd = new InterstitialAd(ChallengeActivity.this);
        interstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                interstitialAd.show();
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        txtTitle = (TfTextView) toolbar.findViewById(R.id.txtTitle);
        txtTitle.setText("India V/S Pakistan");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                doFinish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        doFinish();
    }

    private void doFinish() {
        Functions.hideKeyPad(ChallengeActivity.this, etsearchaddinvite);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
