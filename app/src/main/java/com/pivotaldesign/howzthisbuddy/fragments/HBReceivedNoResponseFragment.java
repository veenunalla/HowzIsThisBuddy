/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.adapter.HBOpinionReceivedNoResponseAdapter;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;

/**
 * @author Satish Kolawale
 *
 */
public class HBReceivedNoResponseFragment extends Fragment {

	private ListView _listNoResponse = null;	 
	private HBReceivedFragment hbrf=new HBReceivedFragment();
	private AppUtilities au=new AppUtilities(getActivity());
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_received_noresponse, container, false);
        _listNoResponse = (ListView) rootView.findViewById(R.id.list_opinion_received_detail_no_response);
		
		String contacts[] = new String[hbrf.al_opinionrespondedpendinglist1.size()];
		for (int index = 0; index < contacts.length; index++) {
			/*String name=au.getContactName(getActivity(), ""+hbrf.al_opinionrespondedpendinglist1.get(index).getResponsePhoneNumber());
			if(name.equalsIgnoreCase(""+hbrf.al_opinionrespondedpendinglist1.get(index).getResponsePhoneNumber())){
				
			}*/
			String number=Long.toString(hbrf.al_opinionrespondedpendinglist1.get(index).getResponsePhoneNumber());
			String name=au.getContactName(getActivity(), number);
			contacts[index] = ""+name;
		}
		
		HBOpinionReceivedNoResponseAdapter adapter = new HBOpinionReceivedNoResponseAdapter(getActivity(), contacts);
		_listNoResponse.setAdapter(adapter);
        
        return rootView;
    }
}