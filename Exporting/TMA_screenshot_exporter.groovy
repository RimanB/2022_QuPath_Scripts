import javax.imageio.ImageIO
import qupath.lib.regions.RegionRequest

// Define resolution - 1.0 means full size
double downsample = 4.0 //Downsample factor when exporting raw images and their overlays
output_fmt='.jpg' //Supported formats include .png, .tif, .jpg. Default compression scheme for each format
IO_fmt='JPG' //Must be set to the capitalized and dot-removed version of output_fmt




// Create output directory inside the project
def dirOutput = buildFilePath(PROJECT_BASE_DIR, 'cores_raw')
mkdirs(dirOutput)


// Write the cores
def server = getCurrentImageData().getServer()
def path = server.getPath()
for (core in getTMACoreList()){
    // Stop if Run -> Kill running script is pressed   
    if (Thread.currentThread().isInterrupted())
        break
    // Write the image
    img = server.readBufferedImage(RegionRequest.createInstance(path, downsample, core.getROI()))
    ImageIO.write(img, IO_fmt, new File(dirOutput, core.getName() + output_fmt))
}
print('Finished exporting individual core screenshots for current opened image')

// Write the full image, displaying objects according to how they are currently shown in the viewer

server = getCurrentServer()
def name = getProjectEntry().getImageName()
def viewer = getCurrentViewer()

getCurrentHierarchy().getTMAGrid().getTMACoreList().each{
    mkdirs(buildFilePath(PROJECT_BASE_DIR,'cores_overlay'))
    path = buildFilePath(PROJECT_BASE_DIR,'cores_overlay',it.getName()+output_fmt)
    def request = RegionRequest.createInstance(server.getPath(), downsample, it.getROI())
    // Stop if Run -> Kill running script is pressed   
    if (Thread.currentThread().isInterrupted())
        break
    writeRenderedImageRegion(viewer,request, path)
}
print('Finished exporting individual core screenshots with overlays for current opened image')