// Checks the generated directory exists
def sido = new File(basedir, "target/generated-sources/sido")
if (!sido.exists()) {
    return "${sido.absolutePath} directory does not exist."
}
// Checks the generated resources directories exists
def sidoResources = new File(basedir, "target/generated-sources/sido-resources")
if (!sidoResources.exists()) {
    return "${sidoResources.absolutePath} directory does not exist."
}

// OK
return true;