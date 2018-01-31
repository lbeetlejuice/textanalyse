package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.MainController;
import model.MetadataDocument;

/**
 * Algorithmen und Datenstrukturen FS 2016,
 * Praktikum 11 - Textanalyse,
 * MainServlet
 * 
 * @author Lukas Buchter, Frank Holzach, Savin Niederer
 * @version 20160524
 */
@WebServlet("/Analyse")
public class MainServlet extends HttpServlet {
	// TODO: Configuration of the folder containing the review files
	private static final MainController maincontroller = new MainController("C:/Temp/reviewsbymembers50000");

	private static final long serialVersionUID = -2601888451202539933L;
	private static final String PARAM_ACTION = "action";
	private static final String PARAM_SEARCH = "name";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding("UTF-8");
		}

		doGet(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String url;
		String action = (request.getParameter(PARAM_ACTION) == null) ? "search" : request.getParameter(PARAM_ACTION);

		switch (action) {
		case "statistics":
			url = DoStatistics(request);
			break;

		case "cloud":
			url = DoCloud(request);
			break;

		case "search":
		default:
			url = DoSearch(request);
			break;
		}

		// Forward to JSP
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	private String DoStatistics(HttpServletRequest request) {
		String numberOfDocuments = "" + maincontroller.getNumberOfDocuments();
		String numberOfWords = "" + maincontroller.getNumberOfWords();

		request.setAttribute("numberOfDocuments", numberOfDocuments);
		request.setAttribute("numberOfWords", numberOfWords);
		request.setAttribute("numberOfDifferentWords", maincontroller.getNumberOfDifferentWords());
		request.setAttribute("topList", maincontroller.getTopList());
		request.setAttribute("totalSizeOfDocuments", maincontroller.getTotalSizeOfDocuments());
		request.setAttribute("averageDocumentSize", maincontroller.getTotalSizeOfDocumentsInBytes() / maincontroller.getNumberOfDocuments());

		return "/WEB-INF/pages/statistics.jsp";
	}

	private String DoCloud(HttpServletRequest request) {
		request.setAttribute("topList", maincontroller.getTopList());

		return "/WEB-INF/pages/cloud.jsp";
	}

	private String DoSearch(HttpServletRequest request) {
		ArrayList<String> searchStringIn = new ArrayList<>();
		ArrayList<String> searchStringNotIn = new ArrayList<>();

		Pattern scanPattern = Pattern.compile("\\S+");
		Pattern replacePattern = Pattern.compile("\\p{Punct}");
		String content = (request.getParameter(PARAM_SEARCH) == null) ? "" : request.getParameter(PARAM_SEARCH);

		if (!content.equals("")) {
			Matcher matcher = scanPattern.matcher(content);
			boolean useORsearch = false;
			boolean useExactSearch = false;
			String useExactSearchToken = "";
			boolean firstRun = true;

			while (matcher.find()) {
				String token = matcher.group();

				if (firstRun) {
					if (token.startsWith("\"") && token.endsWith("\"")) {
						useExactSearchToken = replacePattern.matcher(token).replaceAll("").toLowerCase();
						useExactSearch = true;
						break;
					}
				}
				firstRun = false;

				if (token.startsWith("-")) {
					token = replacePattern.matcher(token).replaceAll("").toLowerCase();
					searchStringNotIn.add(token);
				}
				else {
					if ("OR".equals(token)) {
						useORsearch = true;
					}
					else {
						token = replacePattern.matcher(token).replaceAll("").toLowerCase();
					}

					searchStringIn.add(token);
				}
			}

			if (searchStringIn.isEmpty()) {
				searchStringIn.add("");
			}

			HashMap<MetadataDocument, ArrayList<Integer>> elements;
			if (useORsearch) {
				elements = maincontroller.searchMultipleWordsOR(searchStringIn, searchStringNotIn).getElements();
			}
			else if (useExactSearch) {
				elements = maincontroller.searchWordExact(useExactSearchToken).getElements();
			}
			else {
				elements = maincontroller.searchMultipleWords(searchStringIn, searchStringNotIn).getElements();
			}

			request.setAttribute("elements", elements);
		}

		request.setAttribute("word", (request.getParameter(PARAM_SEARCH) == null) ? "" : request.getParameter(PARAM_SEARCH));

		return "/WEB-INF/pages/search.jsp";
	}

}
