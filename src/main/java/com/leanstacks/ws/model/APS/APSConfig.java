package com.leanstacks.ws.model.APS;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.leanstacks.ws.model.APS.APSConfig.JPEGQuality;

/**
 * AutoPano Server(APS) 작동을 위한 XML 형식 지정
 * @seealso AutopanoServer 4.0(pdf, Generated on 15-03-03) - Output formats 참조
 * @author KIMSEONHO
 *
 */
@XmlRootElement(name = "aps")
public class APSConfig {
	
	@XmlElement(name = "activation")
    public APSActivation activation;

    @XmlElement(name = "group")
    public APSGroup group;

    @XmlElement(name = "application")
    public APSapplication application;

    @XmlElement(name = "pano")
    public APSPano pano;

    public APSConfig() { }

    
    /**
     * example(AutopanoServer, BingleServer/Debug 기준)
     * ../../AutopanoServer/AutopanoServer xml=./Temp/[folderPath]/[folderPath].xml
     * - 기본 설정 : inputFolderPath와 rendered image의 이름이 같다.
     * @param inputFolderPath image folder path
     * @param outputFolderPath rendering path
     */
    public APSConfig(String inputFolderPath, String outputFolderPath)
    {
        activation = new APSActivation();

        application = new APSapplication();
        application.inputFolder = inputFolderPath;

        group = new APSGroup();

        pano = new APSPano();
        pano.renderFolderTpl = outputFolderPath;
    }
	
	/**
	 * Stitching된 output의 품질을 설정함.
	 * @author KIMSEONHO
	 *
	 */
    public enum JPEGQuality {
        LOW1(1),
        LOW2(2),
        Low3(3),
        Low4(4),
        Medium1(5),
        Medium2(6),
        Medium3(7),
        High1(8),
        High2(9),
        Maximum1(10),
        Maximum2(11),
        Maximum3(12);
    	
    	private int value;
    	
    	private JPEGQuality(int value) {
    		this.value = value;
    	}
    }
}

//@XmlElement(name = "activation")
class APSActivation
{
    private String code = "W54T-2DNI-YVGY-CB8E-HSZK-DJGZ-JSTA";
    public boolean userInfo = true;
}

//[XmlElement("group")]
class APSGroup
{
    @XmlElement(name = "level_linkMode")
    protected int levelLinkMode = 0;

    @XmlElement(name = "stack_linkMode")
    protected int stackLinkMode = 1;
}

//[XmlElement("application")]
class APSapplication
{
    public String inputFolder;

    @XmlElement(name = "detection_templateMode")
    public int detectionTemplateMode = 0;

    /**
     * false: multiple panoramas will be created if not all the images are linked together (default)
     * true: Force every image to be in one panorama 
     */
    @XmlElement(name = "isolate_allinone")
    public boolean isolateAllinone = true;
    
    /**
     * not provided or 0: no log
     * 1: for logging messages in the console
     * 2: for logging messages in a file. The filename will have the same name as the rendered panorama added with .log as suffix
     * 3: for logging messages in the console and in the file
     */
    @XmlElement(name = "Log")
    public int log = 2;
}

/**
 * "jpg", "png"
 *  default : jpg
 * @author KIMSEONHO
 *
 */
class APSPano
{
    /**
     * rendering path
     */
	@XmlElement(name = "render_folderTpl")
    public String renderFolderTpl = "";

    /**
     * denotes the name of the generated image. Some special variables can be used: 
     * %a : Name of the project
     */
	@XmlElement(name = "render_filenameTpl")
    public String renderFilenameTpl = "%a";

    /**
     * denotes the path where the generated .pano files will be saved
     * %i : Use image folder as destination folder
     */
	@XmlElement(name = "project_folderTpl")
    public String projectFolderTpl = "%i";

    /**
     * denotes the name of generated .pano files.
     * %R0 : Use image folder name
     */
    @XmlElement(name = "project_filenameTpl")
    public String projectFilenameTpl = "%R0";

    @XmlElement(name = "render_flletype")
    public String renderFileType = "jpg";

    @XmlElement(name = "auto_Save")
    public boolean autoSave = true;

    @XmlElement(name = "auto_Render")
    public boolean autoRender = true;

    @XmlElement(name = "render_percent")
    public int renderPercent = 100;

    @XmlElement(name = "render_fileCompression")
    public JPEGQuality renderFileCompression = JPEGQuality.Maximum1;

    @XmlElement(name = "render_interpolation")
    public int renderInterpolation = 2;

    @XmlElement(name = "render_graphcut")
    public int renderGraphcut = 1;

    @XmlElement(name = "render_blendAlgo")
    public int renderBlendAlgo = 2;

    @XmlElement(name = "render_multiband_level")
    public int renderBlend = -2;

    /**
     * 0: no correction (default), 1: laguerre, 2: hdri
     */
    @XmlElement(name = "colorEqMode")
    public int colorEqMode = 0;

    @XmlElement(name = "auto_Color")
    public boolean autoColor = true;

    /**
     * -1 : automatic, 0: spherical
     */
    @XmlElement(name = "projection")
    public int projection = 0;

    /**
     * fit Image size as projection type
     * equirectangular - w:h = 2:1
     */
    @XmlElement(name = "geo_fitMode")
    public int geoFitMode = 2;
}
