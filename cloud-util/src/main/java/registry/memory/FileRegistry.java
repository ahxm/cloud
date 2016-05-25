package registry.memory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import registry.util.Charsets;
import registry.util.Closeables;
import registry.util.RegistryException;
import registry.util.ZipUtil;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/** XML配置节点数据
 * Created by anzhen on 2016/5/25.
 */
public class FileRegistry extends MemoryRegistry{


    public static final String NODE = "node";
    public static final String PATH = "path";
    public static final String COMPRESS = "compress";
    public static final String ROOT = "root";

    @Override
    protected void doOpen() throws RegistryException {
        if (url == null || url.getPath() == null) {
            return;
        }
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(url.getPath());
        if (in == null) {
            return;
        }
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(in);

            Node node;
            NamedNodeMap attributes;
            Node path;
            Node compress;
            String text;
            byte[] data;
            NodeList nodes = document.getChildNodes();
            // 遍历节点
            for (int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);
                if (node != null && node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(ROOT)) {
                    // 找到根节点，获取子节点
                    nodes = node.getChildNodes();
                    // 遍历子节点
                    for (int j = 0; j < nodes.getLength(); j++) {
                        node = nodes.item(j);
                        // 匹配的节点
                        if (node != null && node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName()
                                .equals(NODE)) {
                            attributes = node.getAttributes();
                            path = attributes.getNamedItem(PATH);
                            compress = attributes.getNamedItem(COMPRESS);
                            text = node.getTextContent();
                            if (text == null) {
                                text = "";
                            }
                            // 确保设置路路径
                            if (path != null) {
                                data = text.getBytes(Charsets.UTF8);
                                if (compress != null && Boolean.valueOf(compress.getNodeValue())) {
                                    create(path.getNodeValue(), ZipUtil.compress(data));
                                } else {
                                    create(path.getNodeValue(), data);
                                }
                            }
                        }
                    }
                    break;
                }
            }
        } catch (ParserConfigurationException e) {
            throw new RegistryException(e);
        } catch (SAXException e) {
            throw new RegistryException(e);
        } catch (IOException e) {
            throw new RegistryException(e);
        } finally {
            Closeables.close(in);
        }
    }

    @Override
    public String getType() {
        return "file";
    }
}
