import qupath.lib.analysis.features.ObjectMeasurements
import qupath.lib.images.ImageData
import qupath.lib.images.servers.ImageServerMetadata
import qupath.lib.images.servers.TransformedServerBuilder
clearDetections()
selectAnnotations()
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImage": "Ir(193)_193Ir-DNA193",  "requestedPixelSizeMicrons": 0.0,  "backgroundRadiusMicrons": 20.0,  "medianRadiusMicrons": 0.0,  "sigmaMicrons": 1.5,  "minAreaMicrons": 10.0,  "maxAreaMicrons": 400.0,  "threshold": 20.0,  "watershedPostProcess": true,  "cellExpansionMicrons": 5.0,  "includeNuclei": true,  "smoothBoundaries": true,  "makeMeasurements": false}');

//Below is what actually calculates the median feature



//def imageData = getCurrentImageData()
//def server = new TransformedServerBuilder(imageData.getServer())
//    .deconvolveStains(imageData.getColorDeconvolutionStains())
//    .build()
server = getCurrentServer() //for IF   
def measurements = ObjectMeasurements.Measurements.values() as List
def compartments = ObjectMeasurements.Compartments.values() as List // Won't mean much if they aren't cells...
def downsample = 1.0



for (detection in getDetectionObjects()) {
  ObjectMeasurements.addIntensityMeasurements(
      server, detection, downsample, measurements, compartments
      )
}

selectDetections();
addShapeMeasurements("AREA", "LENGTH", "CIRCULARITY", "SOLIDITY", "MAX_DIAMETER", "MIN_DIAMETER", "NUCLEUS_CELL_RATIO")
runObjectClassifier("composite_classifier");