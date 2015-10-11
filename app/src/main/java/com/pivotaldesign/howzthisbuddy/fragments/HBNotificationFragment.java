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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

    private OnFragmentInteractionListener mListener;

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
        HBNotificationAdapter adapter = new HBNotificationAdapter(getActivity().getApplicationContext(), notificationModelArrayList);
        notificationListView.setAdapter(adapter);

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
            ArrayList<String[]> getListOfRingTones = getNotifications();
            showDialogForListOfNotificationRingTones(getListOfRingTones);
        } else if (position == 2) {
            ArrayList sounds = getNotificationSoundsURI();
            int x = sounds.size();
        } else {

        }

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
                    Uri notificationRingtoneURI = Uri.parse(notificationRingtone[1] + "/" + notificationRingtone[2]);
//                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    if (ringtone != null) {
                        if (ringtone.isPlaying()) {
                            ringtone.stop();
                        }
                    }
                    ringtone = RingtoneManager.
                            getRingtone(getActivity().getApplicationContext(), notificationRingtoneURI);
                    ringtone.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        dialog.show();
    }

    public ArrayList<String> getNotificationSoundsURI() {
        RingtoneManager manager = new RingtoneManager(getActivity().getApplicationContext());
        manager.setType(RingtoneManager.TYPE_NOTIFICATION);
        Cursor cursor = manager.getCursor();

        ArrayList<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
            String uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);

            list.add(uri + "/" + id);
        }

        return list;
    }


    public ArrayList<String[]> getNotifications() {
        RingtoneManager manager = new RingtoneManager(getActivity().getApplicationContext());
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();
        ArrayList<String[]> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] notificationArray = new String[3];
            String id = cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);

            notificationArray[0] = notificationTitle;
            notificationArray[1] = notificationUri;
            notificationArray[2] = id;
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
