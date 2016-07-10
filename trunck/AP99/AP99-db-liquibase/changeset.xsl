<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
  xmlns="http://www.liquibase.org/xml/ns/dbchangelog">
  
  <xsl:output name="liquibase" method="xml" indent="yes"/>
  
  <!-- newline and <xsl:text> used in SQL statements, critical for LiquiBase hash value -->
  <xsl:variable name="newline">
    <xsl:text>&#xa;</xsl:text>
  </xsl:variable>
  <xsl:variable name="cdata-begin">
    <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
  </xsl:variable>
  <xsl:variable name="cdata-end">
    <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
  </xsl:variable>
  
  <xsl:template match="/databaseChangeLog" xpath-default-namespace="http://www.liquibase.org/xml/ns/dbchangelog">
    <xsl:for-each select="changeSet[exists(createTable)]">
      <xsl:variable name="tablename" select="createTable/@tableName"/>
      <xsl:variable name="filename-index" select="concat($tablename,'/index','.xml')"/>
      <xsl:variable name="filename-db" select="concat($tablename,'/',$tablename,'.xml')"/>
      
      <xsl:result-document href="target/table/{$filename-db}" format="liquibase">
        <databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
          xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.3.xsd
                              http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">
          <xsl:copy-of select="/databaseChangeLog/changeSet[createTable/@tableName=$tablename]"/>
          <xsl:copy-of select="/databaseChangeLog/changeSet[addPrimaryKey/@tableName=$tablename]"/>
          <!--xsl:copy-of select="/databaseChangeLog/changeSet[addForeignKeyConstraint/@baseTableName=$tablename]"/-->
          <xsl:apply-templates select="../changeSet[addForeignKeyConstraint/@baseTableName=$tablename]"/>
          <xsl:copy-of select="/databaseChangeLog/changeSet[createIndex/@tableName=$tablename]"/>
        </databaseChangeLog>
      </xsl:result-document>
    </xsl:for-each>
    
    <xsl:for-each select="changeSet[createSequence]">
      <xsl:variable name="sequenceId" select="createSequence/@sequenceName"/>
      <xsl:if test="contains($sequenceId, '__') and starts-with($sequenceId, 'S_')">
        <xsl:result-document href="target/sequence/T{substring(substring-before($sequenceId,'__'),2)}/{$sequenceId}.xml" format="liquibase">
          <xsl:call-template name="createSequence">
            <xsl:with-param name="sequenceId" select="$sequenceId"/>
          </xsl:call-template>
        </xsl:result-document>
      </xsl:if>
      <xsl:if test="not(contains($sequenceId, '__'))">
        <xsl:result-document href="target/sequence/{$sequenceId}.xml" format="liquibase">
          <xsl:call-template name="createSequence">
            <xsl:with-param name="sequenceId" select="$sequenceId"/>
          </xsl:call-template>
        </xsl:result-document>
      </xsl:if>
    </xsl:for-each>
  </xsl:template>
  
  <xsl:template match="changeSet[addForeignKeyConstraint]" xpath-default-namespace="http://www.liquibase.org/xml/ns/dbchangelog">
    <changeSet author="generated">
      <xsl:attribute name="id" select="@id"/>
      <addForeignKeyConstraint>
        <xsl:attribute name="constraintName" select="addForeignKeyConstraint/@constraintName"/>
        <xsl:attribute name="baseTableName" select="addForeignKeyConstraint/@baseTableName"/>
        <xsl:attribute name="baseColumnNames" select="addForeignKeyConstraint/@baseColumnNames"/>
        <xsl:attribute name="referencedTableName" select="addForeignKeyConstraint/@referencedTableName"/>
        <xsl:attribute name="referencedColumnNames" select="addForeignKeyConstraint/@referencedColumnNames"/>
      </addForeignKeyConstraint>
    </changeSet>
  </xsl:template>
  
  <xsl:template name="createSequence">
    <xsl:param name="sequenceId"/>
    <databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.0.xsd">
      <changeSet author="generated" id="{$sequenceId}">
        <createSequence sequenceName="{$sequenceId}"/>
      </changeSet>
    </databaseChangeLog>
  </xsl:template>
  
</xsl:stylesheet>
