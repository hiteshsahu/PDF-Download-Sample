package com.serveroverload.downloadfile;

import java.io.File;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

	private DownloadTask downloader;

	public PlaceholderFragment() {
	}

	EditText webURl;
	TextView errorConsole;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		// File file = new File(getActivity().getFilesDir(), "PDF");

		webURl = (EditText) rootView.findViewById(R.id.web_url);
		errorConsole = (TextView) rootView.findViewById(R.id.logs);

		// Make Document Directory
		File documentDirectory = new File(getActivity().getFilesDir()
				+ "documents");

		if (!documentDirectory.exists()) {

			if (!documentDirectory.mkdir()) {
				errorConsole.setText("Filed to make Documents directory");
			}
		}

		rootView.findViewById(R.id.start_download).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						String downloadUrl = webURl.getText().toString();

						if (validateUrl(downloadUrl)) {

							String fileName = downloadUrl.substring(downloadUrl
									.lastIndexOf('/') + 1);

							downloader = new DownloadTask(getActivity(),
									errorConsole, new File(new File(
											getActivity().getFilesDir()
													+ "documents"),

									/*
									 * + new SimpleDateFormat(
									 * "yyyyMMdd_HHmmss") .format(new Date()) +
									 * "_"
									 */
									fileName), "downloading");
							downloader.execute(downloadUrl);

						} else {

							errorConsole.setText("Invalid URl");

						}
					}

				});

		return rootView;
	}

	private boolean validateUrl(String downloadUrl) {
		return null != downloadUrl && !downloadUrl.isEmpty()
				&& URLUtil.isValidUrl(downloadUrl);
	}
}