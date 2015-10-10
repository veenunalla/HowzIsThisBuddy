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
import com.pivotaldesign.howzthisbuddy.adapter.HBGivenResponseAdapter;
import com.pivotaldesign.howzthisbuddy.model.HBGivenResponse;
import com.pivotaldesign.howzthisbuddy.model.HBNotifier;

/**
 * @author Satish Kolawale
 *
 */
public class HBGivenDetailFragment extends Fragment {

	private ListView _listGiven = null;	 
	private HBNotifier _notifier = null;
	HBGivenFragment hbgf=new HBGivenFragment(_notifier);
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_given_detail, container, false);
        _listGiven = (ListView) rootView.findViewById(R.id.list_opinion_given_detail_given);
        
        HBGivenResponse responses[] = new HBGivenResponse[hbgf.opi_giv_det_bo.getOpinionsGiven().size()];
		for (int index = 0; index < responses.length; index++) {
			HBGivenResponse response = new HBGivenResponse();
			response.setPrice(hbgf.opi_giv_det_bo.getOpinionsGiven().get(index).getItemBO().getPrice());
			response.setProductName(hbgf.opi_giv_det_bo.getOpinionsGiven().get(index).getItemBO().getItemDesc());
			response.setSelfieUrl(hbgf.opi_giv_det_bo.getOpinionsGiven().get(index).getItemSelfieDetailsBO().getSelfiePic().toString());
			response.setGivenStatus((int)hbgf.opi_giv_det_bo.getOpinionsGiven().get(index).getOpinionBO().getOpinionTypeCode());
			responses[index] = response;
		}
		
		HBGivenResponseAdapter adapter = new HBGivenResponseAdapter(getActivity(), responses);
		_listGiven.setAdapter(adapter);
        
        return rootView;
    }
}