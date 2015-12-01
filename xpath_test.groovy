import javax.xml.xpath.*
import javax.xml.parsers.DocumentBuilderFactory

def testxml = '''
<ClinicalDocument xmlns="urn:hl7-org:v3" xmlns:voc="urn:hl7-org:v3/voc" xmlns:mif="urn:hl7-org:v3/mif" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <typeId extension="POCD_HD000040" root="2.16.840.1.113883.1.3"/>
  <id root="2.16.858.2.10002886.67430.20130530150748.1" />
  <code code="55752-0" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Antecedentes" />
  <title>Antecedentes</title>
  <effectiveTime value="20121224093000-0300" />
  <confidentialityCode code="N" codeSystem="2.16.840.1.113883.5.25" />
  <languageCode code="es-UY"/>
  <setId root="db734647-fc99-424c-a864-7e3cda82e703"/>
  <versionNumber value="1" />
  <recordTarget>
    <patientRole>
      <id root="2.16.858.1.858.68909" extension="41162380" />
      <patient>
        <name>
            <given>Pablo</given>
            <given>Federico</given>
            <family>Pazos</family>
            <family>Gutierrez</family>
        </name>
        <administrativeGenderCode code="1" displayName="Masculino" codeSystem="2.16.858.2.10000675.69600" />
        <birthTime value="19811024" />
      </patient>
    </patientRole>
  </recordTarget>
  <author>
    <time value="20121224093000-0300" />
    <assignedAuthor>
      <id root="2.16.858.2.10000675.69586" extension="12345" />
      <assignedPerson>
         <name>
            <prefix>Dr.</prefix>
            <given>Juan</given>
            <family>Perez</family>
         </name>
      </assignedPerson>
      <representedOrganization>
         <id root="2.16.858.0.2.16.86.1.0.0.123" />
         <name>Hospital X</name>
      </representedOrganization>
    </assignedAuthor>
  </author>
  <custodian>
    <assignedCustodian>
      <representedCustodianOrganization>
        <id root="2.16.858.0.2.16.86.1.0.0.123" />
         <name>Hospital X</name>
      </representedCustodianOrganization>
    </assignedCustodian>
  </custodian>
  <component>
    <structuredBody>
      <component>
        <section>
          <code code="11450-4" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Pronostico y Egreso"/>
          <title>Pronostico y Egreso</title>
          <text>
            <list>
              <item>Condiciones de egreso: compensado</item>
              <item>Pronostico: bueno</item>
              <item>Tipo de egreso: alta transitoria</item>
              <item>Plan e indicaciones: reposo, coordinar fisioterapia</item>
            </list>
          </text>
        </section>
      </component>
      <component>
        <section>
          <code code="11450-5" codeSystem="2.16.840.1.113883.6.1" displayName="Lista de problemas" />
          <title>problemas</title>
          <text>
            <table>
              <thead>
                <tr>
                  <th>Antecedente</th>
                  <th>Activo</th>
                  <th>Fecha (desde .. hasta)</th>
                  <th>Valor</th>
                  <th>Responsable</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>VARICELA</td>
                  <td>No</td>
                  <td>2013-06-02 .. 2013-06-19</td>
                  <td>-</td>
                  <td>Dr. Carlos Solrac</td>
                </tr>
                <tr>
                  <td>PARTOS</td>
                  <td>-</td>
                  <td>(1) 2012-06-02 .. 2012-06-02, (2) 2015-01-02 .. 2015-01-02</td>
                  <td>2</td>
                  <td>Dr. Juan Nauj, Dra. Maria Airam</td>
                </tr>
                <tr>
                  <td>HIPERTENSION ARTERIAL</td>
                  <td>Si</td>
                  <td>2012-11-28 .. ?</td>
                  <td>-</td>
                  <td>Dr. Miguel Leugim</td>
                </tr>
              </tbody>
            </table>
          </text>
        </section>
      </component>
    </structuredBody>
  </component>
</ClinicalDocument>'''

def processXml( String xml, String xpathQuery ) {
  def xpath = XPathFactory.newInstance().newXPath()
  def builder     = DocumentBuilderFactory.newInstance().newDocumentBuilder()
  def inputStream = new ByteArrayInputStream( xml.bytes )
  def records     = builder.parse(inputStream).documentElement
  def nodes = xpath.evaluate( xpathQuery, records, XPathConstants.NODESET )
  def res = []
  nodes.each { node ->
    if (node.textContent.trim()) res << node.textContent
  }
  return res
}

def res = processXml( testxml, '/ClinicalDocument/author//text()' )
println res.size()
println res

