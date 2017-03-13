package com.example.android.sample.myrssreader.parser;

import android.provider.DocumentsContract;

import com.example.android.sample.myrssreader.data.Link;
import com.example.android.sample.myrssreader.data.Site;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * RSSのバージョンを判定するパーサ。
 */
public class RSSParser {

    /**
     * 解析結果のSite。
     */
    private Site site;

    /**
     * 解析結果のリンクリスト。
     */
    private List<Link> links;

    /**
     * RSSフィードの入力ストリームを解析する。
     *
     * @param in
     * @return
     */
    public boolean parse(InputStream in){
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        dbfactory.setNamespaceAware(false);

        try {
            DocumentBuilder builder = dbfactory.newDocumentBuilder();
            Document document = builder.parse(in);
            in.close();

            // RSSのバージョンを判定し、適切なパーサを得る
            FeedParser parser = getParser(document);

            if(parser != null && parser.parse(document)){
                // 解析成功時
                this.site = parser.getSite();
                this.links = parser.getLinkList();
                return true;
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Site getSite() {
        return site;
    }

    public List<Link> getLinkList() {
        return links;
    }

    /**
     * RSSのバージョンに応じたパーサを返す。
     *
     * @param document
     * @return
     */
    private FeedParser getParser(Document document){

        NodeList children = document.getChildNodes();
        FeedParser parser = null;

        for(int i = 0; i < children.getLength(); i++){
            String childName = children.item(i).getNodeName();

            // rdf:RDFはRSS1.0、rssはRSS2.0とする
            if(childName.equals("rdf:RDF")){
                parser = new RSS1Parser();
            }else if(childName.equals("rss")){
                parser = new Rss2Parser();
            }
        }
        return parser;
    }
}
