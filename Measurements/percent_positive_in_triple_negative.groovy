//Delete any vessel objects that can complicate the annotation export at the end
selectObjectsByClassification("CD31+ vessel");
clearSelectedObjects(true);
//Resolve heirarchy to update cells that were formerly in vessels to their original parent PIMO annotation
resolveHierarchy()
//Select the triple negative cells
resetDetectionClassifications();
runObjectClassifier("IBA1_CD45_CD31-TripleNegative");
//selectObjectsByClassification("Er(170)_170Er-IBA1: Sm(152)_152Sm-CD45: Nd(145)_145Nd-CD31");
//obj_to_keep=getSelectedObjects()
objs = getCellObjects().findAll{it.getPathClass() != getPathClass("Er(170)_170Er-IBA1: Sm(152)_152Sm-CD45: Nd(145)_145Nd-CD31")}
//Remove the cells from the image that are NOT the triple negatives
removeObjects(objs,true)

//------------------------------------------
//Begin counting percent single positives
//Now that we've removed all cells that aren't of interest to us, lets start counting the number of percent single positives per annotation
//To do this, we need to create and apply a composite classifier containing all our classes of interest, and specify it below in runObjectClassifier
//Theoretically, we can use the composite classifier containing all classes
//Then, we need to specify the classes that we are particularly interested in getting percent single positives for, in baseClasses
//------------------------------------------

runObjectClassifier("HK2-GLUT1-LDHA-CA9-ICAM1-Ki67-TMHistone");
baseClasses=[
  "Dy(163)_163Dy-HK2",
  "Dy(164)_164Dy-LDHA",
  "Yb(173)_173Yb-TMHistone",
  "Er(168)_168Er-Ki67",
  "Gd(160)_160Gd-GLUT1",
  "Yb(174)_174Yb-ICAM",
  "Eu(151)_151Eu-CA9"
]


for (annotation in getAnnotationObjects()){

        totalCells = getCurrentHierarchy().getObjectsForROI(qupath.lib.objects.PathCellObject, annotation.getROI())
print totalCells.size()

        for (aClass in baseClasses){

            if (totalCells.size() > 0){
                cells = totalCells.findAll{it.getPathClass().toString().contains(aClass)}

                if (true) {annotation.getMeasurementList().putMeasurement("All "+aClass+" %", cells.size()*100/totalCells.size())}
                annotationArea = annotation.getROI().getArea()
            } else {
                if (percentages) {annotation.getMeasurementList().putMeasurement("All "+aClass+" %", 0)}

            }

        }

    }