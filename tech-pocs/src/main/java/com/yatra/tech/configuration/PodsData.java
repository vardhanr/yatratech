package com.yatra.tech.configuration;

public class PodsData {

    public static void main(String[] args) {
	generatePodsUrl();
    }

    
    public static void generatePodsUrl() {
	String podsName = "xdist5-2190601950-s6651,xdist5-3205689059-7b1k4,xdist5-3205689059-7lzkk,xdist5-3205689059-fqrxq,xdist5-3205689059-llz1n,xdist5-3205689059-nw7xt,xdist5-3205689059-q79mr,xdist5-3205689059-s2vc9,xdist5-3205689059-vrdqc,xdist5-3205689059-vv1jb,xdist5-3205689059-z830p,xdist5-3205689059-zrzjw,xdist5-3205689059-046z9,xdist5-3205689059-0dxxs,xdist5-3205689059-6415p,xdist5-3205689059-gvgpz,xdist5-3205689059-vbth1,xdist5-3205689059-m97sr,xdist5-3095914623-0d5v1,xdist5-3095914623-70bt7,";
	String[] pods = podsName.split(",");
	String from = "1509513492742";
	String to = "1509862747369";
	StringBuilder urlPrefix = new StringBuilder("http://grafana.yatra.com/dashboard/db/pods?");
	urlPrefix.append("orgId=1&var-namespace=default&from=" + from);
	urlPrefix.append("&to=" + to);

	for (String pod : pods) {
	    String url = urlPrefix.toString() + "&var-podname=" + pod;
	    System.out.println(url);
	}
    }
}
