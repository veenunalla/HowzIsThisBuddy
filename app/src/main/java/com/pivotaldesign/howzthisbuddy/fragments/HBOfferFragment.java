/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.adapter.HBOfferAdapter;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.model.HBOffer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Satish Kolawale
 *
 */
public class HBOfferFragment extends Fragment{

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_offers, container, false);
        
        ((TextView) rootView.findViewById(R.id.txt_offers_title)).setTypeface(HBApplication.getInstance().getNormalFont());
        
        ListView listOffer = (ListView) rootView.findViewById(R.id.list_offers);
        
        HBOffer offers[] = new HBOffer[10];
        
        for(int index = 0; index < offers.length; index++) {
        	HBOffer offer = new HBOffer();
        	offer.setOfferId( (index + 1) );
        	offer.setOfferDescription("Offer description Offer description Offer description Offer description - "  + (index + 1));
        	offers[index] = offer;
        }
        
        HBOfferAdapter adapter = new HBOfferAdapter(getActivity(), offers);
        listOffer.setAdapter(adapter);
         
        return rootView;
    }
}
