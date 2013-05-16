package com.hawserver.veranstaltungsplan;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class DownloadScheduleTask extends TimerTask {

	private final String DEFAULT_SOURCE = "http://www.informatik.haw-hamburg.de/fileadmin/Homepages/ProfPadberg/stundenplaene/Sem_I.txt";
	private final String DEFAULT_DESTINATION = "files/Sem_I.txt";
	private final String rootPath;
	private final static Logger LOG = Logger
			.getLogger(DownloadScheduleTask.class);

	public DownloadScheduleTask(String rootPath) {
		this.rootPath = rootPath;
	}

	@Override
	public void run() {
		try {
			FileUtils.copyURLToFile(new URL(DEFAULT_SOURCE), new File(rootPath
					+ DEFAULT_DESTINATION));
			LOG.info("Successfully downloaded file.");
		} catch (MalformedURLException e) {
			LOG.warn("Failed to download file", e);
		} catch (IOException e) {
			LOG.warn("Failed to download file", e);
		}
	}

}
