package info.androidhive.slidingmenu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class HomeFragment extends Fragment {

	private ListView userList;	
	 ArrayList<Pocustom> countryList = new ArrayList<Pocustom>();
	 MyCustomAdapter dataAdapter = null;
	ArrayAdapter<String> adapter;	 
	 
	 String result = null;	
	
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		countryList.clear();
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        
        String val_branch="INTERNATIONAL" ;
        StrictMode.enableDefaults();	
    	userList = (ListView) getActivity().findViewById(R.id.listView1); 
    	
    	InputStream webs = null;
		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			//val_branch=val_branch.replace(" ", "");
			String surl = "http://117.232.65.99:8080/Purchase/Order.php?var1=International";
			HttpPost httppost = new HttpPost(surl);			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			webs = entity.getContent();	
			
		}
		catch (Exception e)
		{				
	   Toast.makeText(getActivity().getApplicationContext(), "Network Not Available", Toast.LENGTH_SHORT).show();
	   		   
		}
		try
		{
		BufferedReader br = new BufferedReader(new InputStreamReader(webs,"iso.8859-1"),8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while((line=br.readLine())!=null)
		{
		sb.append(line+"/n");	
		}
		
		webs.close();
		
		result=sb.toString() ;
		}
	   catch(Exception e)
	   {
		   
	   }
		 try {	   
		 		
			  
	    	   JSONArray jArray = new JSONArray(result);	    	  
	    	   String[] DOCID = new  String[jArray.length()];		    	   
	    	   String[] DOCDATE = new  String[jArray.length()];
	     	   String[] CATEGORY = new  String[jArray.length()];
	     	   String[] PARTY = new  String[jArray.length()];
	     	   String[] net = new  String[jArray.length()];
	     	//   String[] CWORKDESC = new  String[jArray.length()];
	    	   
	    	   for (int j = 0; j < jArray.length(); j++) {
	    		   		  
	    		   JSONObject Json = jArray.getJSONObject(j);
	    		   DOCID[j] =Json.getString("DOCID");
	    		   DOCDATE[j] =Json.getString("DOCDATE");
	    		   CATEGORY[j]=  Json.getString("CATEGORY");
	    		   PARTY[j]=Json.getString("PARTYNAME");
	    		   net[j]=Json.getString("net");
	    		  // CWORKDESC[j]=Json.getString("CWORKDESC");
	    		   Pocustom country = new Pocustom(DOCID[j],DOCDATE[j],CATEGORY[j],PARTY[j],net[j] );
	    		   countryList.add(country);
	    		   
	    		  // Toast.makeText(getActivity().getApplicationContext(), DOCID[j], Toast.LENGTH_SHORT).show();
	    		   }		    	
	    	 
		  }
		  catch(Exception e)
		  {
		  }
		 
		
		 
		 dataAdapter = new MyCustomAdapter(getActivity(),
				 R.layout.po_custom , countryList);
				ListView listView = (ListView) rootView.findViewById(R.id.listView1);
				// Assign adapter to ListView
				listView.setAdapter(dataAdapter);

				//enables filtering for the contents of the given ListView
			//	setTextFilterEnabled(true);
				
				listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				  Pocustom country = (Pocustom) parent.getItemAtPosition(position);
					// Toast.makeText(getActivity().getApplicationContext(),
					  //country.getCode(), Toast.LENGTH_SHORT).show();
					String PO_DOCID=country.getCode();
					 
					// Toast.makeText(getActivity().getApplicationContext(), PO_DOCID, Toast.LENGTH_SHORT).show();
					 
				//	Intent oritem = new Intent(getActivity(),info.androidhive.slidingmenu.Itemorder.class);
		           
				  //     oritem.putExtra("id", PO_DOCID); 	
					
					 Fragment duedateFrag = new Fragmenttest();
					 android.app.FragmentTransaction ft  = getFragmentManager().beginTransaction();
					 ft.replace(R.id.frame_container, duedateFrag);
					 ft.addToBackStack(null);
					 ft.commit();
					 Bundle bundle = new Bundle();
					 bundle.putString("position", PO_DOCID);
					 duedateFrag.setArguments(bundle);
					 
					 
					}
					});

        
        return rootView;
    }
	
	private void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		// TODO Auto-generated method stub
		
	}

	private void setTextFilterEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}

	private void setListAdapter(MyCustomAdapter dataAdapter2) {
		// TODO Auto-generated method stub
		
	}
	
	

	private class MyCustomAdapter extends ArrayAdapter<Pocustom> {
		 
		  private ArrayList<Pocustom> originalList;
		  private ArrayList<Pocustom> countryList;
		  private CountryFilter filter;
		 
		  public MyCustomAdapter(Context context, int textViewResourceId,
		    ArrayList<Pocustom> countryList) {
		   super(context, textViewResourceId, countryList);
		   this.countryList = new ArrayList<Pocustom>();
		   this.countryList.addAll(countryList);
		   this.originalList = new ArrayList<Pocustom>();
		   this.originalList.addAll(countryList);
		  }
		 
		  @Override
		  public Filter getFilter() {
		   if (filter == null){
		    filter  = new CountryFilter();
		   }
		   return filter;
		  }
		 
		 
		  private class ViewHolder {
		   TextView code;
		   TextView name;
		   TextView continent;
		   TextView region;
		   TextView nkl;
		   TextView capex;
		  }
		 
		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		 
		   ViewHolder holder = null;
		   Log.v("ConvertView", String.valueOf(position));
		   if (convertView == null) {
		 
		   LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(
		     Context.LAYOUT_INFLATER_SERVICE);
		   convertView = vi.inflate(R.layout.po_custom, null);
		 
		   holder = new ViewHolder();
		   holder.code = (TextView) convertView.findViewById(R.id.code);
		   holder.name = (TextView) convertView.findViewById(R.id.name);
		   holder.continent = (TextView) convertView.findViewById(R.id.continent);
		   holder.region = (TextView) convertView.findViewById(R.id.region);
		   holder.nkl = (TextView) convertView.findViewById(R.id.textView6);
		 //  holder.capex = (TextView) convertView.findViewById(R.id.textView8);
		 
		   convertView.setTag(holder);
		 
		   } else {
		    holder = (ViewHolder) convertView.getTag();
		   }
		 
		   Pocustom country = countryList.get(position);
		   holder.code.setText(country.getCode());
		   holder.name.setText(country.getName());
		   holder.continent.setText(country.getContinent());
		   holder.region.setText(country.getRegion());
		   holder.nkl.setText(country.getnet());
		  // holder.capex.setText(country.getcapex());
		 
		   return convertView;
		 
		  }
		 
		  private class CountryFilter extends Filter
		  {
		 
		   @Override
		   protected FilterResults performFiltering(CharSequence constraint) {
		 
		    constraint = constraint.toString().toLowerCase();
		    FilterResults result = new FilterResults();
		    if(constraint != null && constraint.toString().length() > 0)
		    {
		    ArrayList<Pocustom> filteredItems = new ArrayList<Pocustom>();
		 
		    for(int i = 0, l = originalList.size(); i < l; i++)
		    {
		     Pocustom country = originalList.get(i);
		     if(country.toString().toLowerCase().contains(constraint))
		      filteredItems.add(country);
		    }
		    result.count = filteredItems.size();
		    result.values = filteredItems;
		    }
		    else
		    {
		     synchronized(this)
		     {
		      result.values = originalList;
		      result.count = originalList.size();
		     }
		    }
		    return result;
		   }
		 
		   @SuppressWarnings("unchecked")
		   @Override
		   protected void publishResults(CharSequence constraint,
		     FilterResults results) {
		 
		    countryList = (ArrayList<Pocustom>)results.values;
		    notifyDataSetChanged();
		    clear();
		    for(int i = 0, l = countryList.size(); i < l; i++)
		     add(countryList.get(i));
		    notifyDataSetInvalidated();
		   }
		  }
		 
		 
		 }

	
}
