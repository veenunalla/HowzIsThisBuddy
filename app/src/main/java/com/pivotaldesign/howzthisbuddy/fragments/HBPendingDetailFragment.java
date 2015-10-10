/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.util.HashMap;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.adapter.HBGivenResponseAdapter;
import com.pivotaldesign.howzthisbuddy.adapter.HBPendingResponseAdapter;
import com.pivotaldesign.howzthisbuddy.model.HBGivenResponse;
import com.pivotaldesign.howzthisbuddy.model.HBNotifier;

/**
 * @author Satish Kolawale
 *
 */
public class HBPendingDetailFragment extends Fragment {

	private ListView _listPending = null;
	private HBNotifier _notifier = null;
	HBGivenFragment hbgf=new HBGivenFragment(_notifier);
	public static HashMap<String, Integer> hbpfposition;
	public HBPendingDetailFragment(HBNotifier notifier) {
		this._notifier = notifier;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_given_detail, container, false);
        _listPending = (ListView) rootView.findViewById(R.id.list_opinion_given_detail_given);
        hbpfposition=new HashMap<String, Integer>();
        HBGivenResponse responses[] = new HBGivenResponse[hbgf.opi_giv_det_bo.getOpinionsPending().size()];
		for (int index = 0; index < responses.length; index++) {
			HBGivenResponse response = new HBGivenResponse();
			response.setPrice(hbgf.opi_giv_det_bo.getOpinionsPending().get(index).getItemBO().getPrice());
			response.setProductName(hbgf.opi_giv_det_bo.getOpinionsPending().get(index).getItemBO().getItemDesc());
			response.setSelfieUrl(hbgf.opi_giv_det_bo.getOpinionsPending().get(index).getItemSelfieDetailsBO().getSelfiePic().toString());
			responses[index] = response;
		}
		
		HBPendingResponseAdapter adapter = new HBPendingResponseAdapter(getActivity(), responses);
		_listPending.setAdapter(adapter);
		_listPending.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//showResponseActivity();
				//commented by rajesh
				/*Bundle args = new Bundle();
                args.putString("Position", ""+position);*/
				hbpfposition.put("position", position);
				_notifier.notifier(position);
				
				/*Bundle args = new Bundle();
                args.putString("Position", ""+position);
                Fragment detail=new HBResponseFragment();
                detail.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container, detail).commit();*/
				/*Intent i=new Intent();
				i.setClass(getActivity().getApplicationContext(), HBResponseFragment.class);
				i.putExtra("position", position);
				getActivity().startActivity(i);
				Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();*/
			}
		});
		
		
        
        return rootView;
    }
	
}