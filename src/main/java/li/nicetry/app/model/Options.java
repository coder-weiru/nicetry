package li.nicetry.app.model;

public class Options {

	public static final String PDF_OUTPUT = "pdf";
	public static final String SCREENSHOT_OUTPUT = "screenshot";

	/*
	 * Url to render. Either url or html is required
	 */
	private String url;

	/*
	 * Either "pdf" or "screenshot"
	 */
	private String output;

	/*
	 * HTML content to render. Either url or html is required
	 */
	private String html;

	/*
	 * If we should emulate @media screen instead of print
	 */
	private boolean emulateScreenMedia;

	/*
	 * If we should ignore HTTPS errors
	 */
	private boolean ignoreHttpsErrors;

	/*
	 * If true, page is scrolled to the end before rendering Note: this makes
	 * rendering a bit slower
	 */
	private boolean scrollPage;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public boolean isEmulateScreenMedia() {
		return emulateScreenMedia;
	}

	public void setEmulateScreenMedia(boolean emulateScreenMedia) {
		this.emulateScreenMedia = emulateScreenMedia;
	}

	public boolean isIgnoreHttpsErrors() {
		return ignoreHttpsErrors;
	}

	public void setIgnoreHttpsErrors(boolean ignoreHttpsErrors) {
		this.ignoreHttpsErrors = ignoreHttpsErrors;
	}

	public boolean isScrollPage() {
		return scrollPage;
	}

	public void setScrollPage(boolean scrollPage) {
		this.scrollPage = scrollPage;
	}

}
