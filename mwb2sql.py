import os, grt
from grt.modules import DbMySQLFE
from workbench.utils import Version

def createScriptForCatalogObjects(path, catalog, objectCreationParams):
    """Create a CREATE script with the catalog objects. The catalog must have been previously processed
    with generateSQLCreateStatements(), so that the objects have their temp_sql attributes set with
    their respective SQL CREATE statements.
    """

    def object_heading(type, name):
        text = """
-- ----------------------------------------------------------------------------
-- %s %s
-- ----------------------------------------------------------------------------
""" % (type, name)
        return text


    import time
    file = open(path, "w+")
    file.write("""-- ----------------------------------------------------------------------------
-- MySQL Workbench Migration
-- Schemata: %s
-- Created: %s
-- Workbench Version: %s
-- ----------------------------------------------------------------------------
""" % (", ".join([s.name for s in catalog.schemata]), time.ctime(), Version.fromgrt(grt.root.wb.info.version)))

    if 'TableExcludedList' not in objectCreationParams:
        objectCreationParams['TableExcludedList'] = []

    preamble = catalog.customData["migration:preamble"]
    if preamble and preamble.temp_sql:
        #file.write(object_heading("Preamble script", ""))
        file.write(preamble.temp_sql+"\n")
    for schema in catalog.schemata:
        file.write(object_heading("Schema", schema.name))
        file.write(schema.temp_sql+";\n")

        for table in schema.tables:
            if(table.name in objectCreationParams['TableExcludedList']):
                continue
            file.write(object_heading("Table", "%s.%s" % (schema.name, table.name)))
            file.write(table.temp_sql+";\n")

        for view in schema.views:
            file.write(object_heading("View", "%s.%s" % (schema.name, view.name)))
            file.write(view.temp_sql+";\n")

        for routine in schema.routines:
            file.write(object_heading("Routine", "%s.%s" % (schema.name, routine.name)))
            file.write(routine.temp_sql)

        for table in schema.tables:
            for trigger in table.triggers:
                file.write(object_heading("Trigger", "%s.%s" % (schema.name, trigger.name)))
                file.write(trigger.temp_sql+";\n")

    postamble = catalog.customData["migration:postamble"]
    if postamble and postamble.temp_sql:
        #file.write(object_heading("Postamble script", ""))
        file.write(postamble.temp_sql+"\n")

    file.close()

    return 1

c = grt.root.wb.doc.physicalModels[0].catalog
DbMySQLFE.generateSQLCreateStatements(c, c.version, {'SkipForeignKeys':1})
# DbMySQLFE.createScriptForCatalogObjects(os.getenv('SQL_PATH'), c, {})
createScriptForCatalogObjects(os.getenv('SQL_PATH'), c, {'TableExcludedList':['mts_entity']})