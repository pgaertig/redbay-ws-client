<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- Identity template: copy content by default -->
    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>

    <!-- Match elements with href attributes -->
    <xsl:template match="*[@href]">
        <!-- Get the ID from the href attribute -->
        <xsl:variable name="refId" select="substring-after(@href, '#')"/>
        <!-- Replace the current element with the referenced content and retain its 'id' attribute -->
        <xsl:element name="{name()}">
            <!-- Copy the attributes excluding 'href' -->
            <xsl:apply-templates select="@*[not(name()='href')]" />
            <xsl:copy-of select="//*[@id=$refId]/*"/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="@id" />

</xsl:stylesheet>
