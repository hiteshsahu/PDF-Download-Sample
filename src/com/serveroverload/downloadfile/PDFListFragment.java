package com.serveroverload.downloadfile;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PDFListFragment extends Fragment {

	private ArrayList<String> filePath;

	public PDFListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_list, container,
				false);

		File documentDirectory = new File(getActivity().getFilesDir()
				+ "documents");

		ListView list = (ListView) rootView.findViewById(R.id.pdf_list);

		File file[] = documentDirectory.listFiles();
		Log.d("Files", "Size: " + file.length);

		filePath = new ArrayList<String>();
		for (int i = 0; i < file.length; i++) {
			Log.d("Files", "FileName:" + file[i].getName());
			filePath.add(file[i].getAbsolutePath());
		}

		if (null != file && null != filePath) {

			list.setAdapter(new ArrayAdapter<String>(getActivity(),
					R.layout.simple_list_item, filePath));
		}

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				MainActivity.currentWebURl = filePath.get(arg2);

				((MainActivity) getActivity()).mViewPager.setCurrentItem(3);

			}
		});

		return rootView;
	}
}