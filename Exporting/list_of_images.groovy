def project = getProject()
def file_list=[]
def pathDetection = buildFilePath(PROJECT_BASE_DIR, 'test.txt')
File file = new File(pathDetection)
file.write "First line\n"
print pathDetection
for (entry in project.getImageList()) {
    //print entry.getImageName()
    file_list.add(entry)
    file << entry
}

