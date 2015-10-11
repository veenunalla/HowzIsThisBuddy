package com.pivotaldesign.howzthisbuddy.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.adapter.HBNotificationAdapter;
import com.pivotaldesign.howzthisbuddy.adapter.HBNotificationRingtoneAdapter;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HBNotificationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HBNotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HBNotificationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Ringtone ringtone;
    private ListView notificationListView;
    private ArrayList<NotificationModel> notificationModelArrayList;
    private String[] selectedRingtone;
    private HBNotificationAdapter notificationAdapter;
    private OnFragmentInteractionListener mListener;
    public static final int NOTIFICATION_TITLE=0;
    public static final int NOTIFICATION_URI=1;
    public static final int NOTIFICATION_ID=2;
     /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HBNotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HBNotificationFragment newInstance(String param1, String param2) {
        HBNotificationFragment fragment = new HBNotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HBNotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hbnotification, container, false);
        initUI(rootView);

        LayoutInflater headerInflater = getActivity().getLayoutInflater();
        ViewGroup header = (ViewGroup) headerInflater.inflate(R.layout.layout_notification_item_header, notificationListView, false);
        TextView notificationHeader = (TextView) header.findViewById(R.id.notification_header);
        notificationHeader.setTypeface(HBApplication.getInstance().getRegularFont());
        notificationListView.addHeaderView(header, null, false);
        notificationAdapter = new HBNotificationAdapter(getActivity().getApplicationContext(), notificationModelArrayList);
        notificationListView.setAdapter(notificationAdapter);

        notificationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                showOptionsForSelections(view, position);
            }
        });


        return rootView;
    }

    private void showOptionsForSelections(View view, int position) {
        if (position == 1) {
            ArrayList<String[]> getListOfRingTones = getAllNotificationsRingtonesList();
            showDialogForListOfNotificationRingTones(getListOfRingTones);
        } else if (position == 2) {
            String[] vibrateOptions = new String[5];
            vibrateOptions[0] = "Vibrate";
            vibrateOptions[1] = "Off";
            vibrateOptions[2] = "Default";
            vibrateOptions[3] = "Short";
            vibrateOptions[4] = "Long";
            showDialogwithRadioButtons(R.layout.layout_notification_alertdialog_radiobuttons, vibrateOptions ,true);
        } else {
            String[] popUpNotificationOptions = new String[5];
            popUpNotificationOptions[0] = "Popup notification";
            popUpNotificationOptions[1] = "No Popup";
            popUpNotificationOptions[2] = "Only when screen \"On\" ";
            popUpNotificationOptions[3] = "Only when screen \"Off\" ";
            popUpNotificationOptions[4] = "Always show popup";
            showDialogwithRadioButtons(R.layout.layout_notification_alertdialog_radiobuttons, popUpNotificationOptions ,false);
        }

    }

    private void showDialogwithRadioButtons(int layoutID, final String[] options, final boolean isVibrateAlertView) {
        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layoutID);

        TextView dialogTitle = (TextView)dialog.findViewById(R.id.radioButton_title);
        dialogTitle.setText(options[0]);

         // set the custom dialog components - text, image and button
        RadioGroup radioGroup = (RadioGroup)dialog.findViewById(R.id.radio_group);
        final RadioButton radioButton1 = (RadioButton)dialog.findViewById(R.id.radioButton_one);
        radioButton1.setText(options[1]);
        final RadioButton radioButton2 = (RadioButton)dialog.findViewById(R.id.radioButton_two);
        radioButton2.setText(options[2]);

        final RadioButton radioButton3= (RadioButton)dialog.findViewById(R.id.radioButton_three);
        radioButton3.setText(options[3]);

        final RadioButton radioButton4 = (RadioButton)dialog.findViewById(R.id.radioButton_four);
        radioButton4.setText(options[4]);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radioButton_one) {
                    saveToSharedPreferenceAndUpdateListView(radioButton1.getText().toString(), isVibrateAlertView);
                    dialog.dismiss();
                } else if (checkedId == R.id.radioButton_two) {
                    saveToSharedPreferenceAndUpdateListView(radioButton2.getText().toString(), isVibrateAlertView);
                    dialog.dismiss();
                } else if (checkedId == R.id.radioButton_three) {
                    saveToSharedPreferenceAndUpdateListView(radioButton3.getText().toString(), isVibrateAlertView);
                    dialog.dismiss();
                } else {
                    saveToSharedPreferenceAndUpdateListView(radioButton4.getText().toString(), isVibrateAlertView);
                    dialog.dismiss();
                }
            }

        });


        Button cancelButton = (Button) dialog.findViewById(R.id.radioButton_canncel);
        // if button is clicked, close the custom dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //Grab the window of the dialog, and change the width
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }

    private void saveToSharedPreferenceAndUpdateListView(String message,boolean isVibrateAlertView) {
        if (isVibrateAlertView){
            notificationModelArrayList.get(1).setNotificationSubItemLabel(message);
        }else {
            notificationModelArrayList.get(2).setNotificationSubItemLabel(message);
        }
        notificationAdapter = new HBNotificationAdapter(getActivity().getApplicationContext(), notificationModelArrayList);
        notificationListView.setAdapter(notificationAdapter);
        notificationAdapter.notifyDataSetChanged();
    }

    private void showDialogForListOfNotificationRingTones(final ArrayList<String[]> getListOfRingTones) {

        ArrayList<String> notificationNamesList = new ArrayList<>();
        Iterator<String[]> tonesIterator = getListOfRingTones.iterator();
        while (tonesIterator.hasNext()) {
            String[] notificationItem = tonesIterator.next();
            notificationNamesList.add(notificationItem[0]);
        }
        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_notification_ringtone_view);
        // set the custom dialog components - text, image and button
        ListView tonesListView = (ListView) dialog.findViewById(R.id.listview_notification_tones_list);
        HBNotificationRingtoneAdapter adapter = new HBNotificationRingtoneAdapter(getActivity().getApplicationContext(), notificationNamesList);
        tonesListView.setAdapter(adapter);

        Button okButton = (Button) dialog.findViewById(R.id.notification_tones_ok_button);
        // if button is clicked, close the custom dialog
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ringtone != null) {
                    if (ringtone.isPlaying()) {
                        ringtone.stop();
                    }
                }
                storeTheRingtoneInSharedPreferenceAndUpdateListView();
                dialog.dismiss();
            }
        });

        Button cancelButton = (Button) dialog.findViewById(R.id.notification_tones_cancel_button);
        // if button is clicked, close the custom dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ringtone != null) {
                    if (ringtone.isPlaying()) {
                        ringtone.stop();
                    }
                }
                dialog.dismiss();
            }
        });

        tonesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                try {

                    String[] notificationRingtone = getListOfRingTones.get(position);
                    Uri notificationRingtoneURI = Uri.parse(notificationRingtone[NOTIFICATION_URI] + "/" + notificationRingtone[NOTIFICATION_ID]);
                    if (ringtone != null) {
                        if (ringtone.isPlaying()) {
                            ringtone.stop();
                        }
                    }
                    ringtone = RingtoneManager.
                            getRingtone(getActivity().getApplicationContext(), notificationRingtoneURI);
                    ringtone.play();
                    selectedRingtone = notificationRingtone;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        String[] notificationRingtone = getListOfRingTones.get(0);
        selectedRingtone = notificationRingtone;
        dialog.show();
    }

    private void storeTheRingtoneInSharedPreferenceAndUpdateListView() {
        notificationModelArrayList.get(0).setNotificationSubItemLabel("Default ringtone"+"("+selectedRingtone[NOTIFICATION_TITLE]+")");
        notificationAdapter = new HBNotificationAdapter(getActivity().getApplicationContext(), notificationModelArrayList);
        notificationListView.setAdapter(notificationAdapter);
        notificationAdapter.notifyDataSetChanged();
    }


    public ArrayList<String[]> getAllNotificationsRingtonesList() {
        RingtoneManager manager = new RingtoneManager(getActivity().getApplicationContext());
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();
        ArrayList<String[]> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] notificationArray = new String[3];
            String id = cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);

            notificationArray[NOTIFICATION_TITLE] = notificationTitle;
            notificationArray[NOTIFICATION_URI] = notificationUri;
            notificationArray[NOTIFICATION_ID] = id;
            list.add(notificationArray);
            Log.d("Notification", notificationTitle);
            Log.d("Notification URI", notificationUri);
        }
        return list;
    }

    private void initUI(View rootView) {
        notificationListView = (ListView) rootView.findViewById(R.id.listview_notification);
        notificationModelArrayList = new ArrayList<>();

        NotificationModel notificationModel = new NotificationModel();
        notificationModel = new NotificationModel();
        notificationModel.setNotificationItemLabel("Notification tone");
        notificationModel.setNotificationSubItemLabel("Default ringtone");
        notificationModelArrayList.add(notificationModel);
        notificationModel = new NotificationModel();
        notificationModel.setNotificationItemLabel("Vibrate");
        notificationModel.setNotificationSubItemLabel("Default");
        notificationModelArrayList.add(notificationModel);
        notificationModel = new NotificationModel();
        notificationModel.setNotificationItemLabel("Popup notification");
        notificationModel.setNotificationSubItemLabel("Always show popup");
        notificationModelArrayList.add(notificationModel);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public static class NotificationModel {
        private String NotificationItemLabel;
        private String NotificationSubItemLabel;


        public String getNotificationItemLabel() {
            return NotificationItemLabel;
        }

        public void setNotificationItemLabel(String notificationItemLabel) {
            NotificationItemLabel = notificationItemLabel;
        }

        public String getNotificationSubItemLabel() {
            return NotificationSubItemLabel;
        }

        public void setNotificationSubItemLabel(String notificationSubItemLabel) {
            NotificationSubItemLabel = notificationSubItemLabel;
        }


    }


}
