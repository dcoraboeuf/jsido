// Checks the generated directory exists
def sido = new File(basedir, "target/generated-sources/sido")
if (!sido.exists()) {
    return "${sido.absolutePath} directory does not exist."
}

// OK
return true;