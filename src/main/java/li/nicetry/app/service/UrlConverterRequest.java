package li.nicetry.app.service;

public class UrlConverterRequest {

	public enum OutputFormat {
		PDF("pdf"), SCREENSHOT("screenshot");

		private final String value;

		OutputFormat(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	// Url to render. Either url or html is required
	private String url;

	// Either "pdf" or "screenshot"
	private String output;

	// HTML content to render. Either url or html is required
	private String html;

	// If we should emulate @media screen instead of print
	private boolean emulateScreenMedia = true;

	// If we should ignore HTTPS errors
	private boolean ignoreHttpsErrors = false;

	// If true, page is scrolled to the end before rendering
	// Note: this makes rendering a bit slower
	private boolean scrollPage = true;

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOutput() {
		return this.output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getHtml() {
		return this.html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public boolean isEmulateScreenMedia() {
		return this.emulateScreenMedia;
	}

	public void setEmulateScreenMedia(boolean emulateScreenMedia) {
		this.emulateScreenMedia = emulateScreenMedia;
	}

	public boolean isIgnoreHttpsErrors() {
		return this.ignoreHttpsErrors;
	}

	public void setIgnoreHttpsErrors(boolean ignoreHttpsErrors) {
		this.ignoreHttpsErrors = ignoreHttpsErrors;
	}

	public boolean isScrollPage() {
		return this.scrollPage;
	}

	public void setScrollPage(boolean scrollPage) {
		this.scrollPage = scrollPage;
	}

}