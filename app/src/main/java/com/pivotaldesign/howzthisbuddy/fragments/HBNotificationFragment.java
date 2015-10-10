package com.pivotaldesign.howzthisbuddy.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.adapter.HBNotificationAdapter;

import java.util.ArrayList;

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

        HBNotificationAdapter adapter = new HBNotificationAdapter(getActivity().getApplicationContext(), notificationModelArrayList);
        notificationListView.setAdapter(adapter);

        return rootView;
    }

    private void initUI(View rootView) {
        notificationListView = (ListView) rootView.findViewById(R.id.listview_notification);
        notificationModelArrayList = new ArrayList<>();

        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setNotificationItemLabel("Conversation tones");
        notificationModel.setNotificationSubItemLabel("Play sounds for incoming and outgoing messages");
        notificationModelArrayList.add(notificationModel);
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
