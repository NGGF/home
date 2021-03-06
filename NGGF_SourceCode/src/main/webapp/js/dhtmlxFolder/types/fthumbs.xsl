<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:variable name="icons_src_dir">.</xsl:variable>
	<xsl:variable name="thumbs_creator_url">.</xsl:variable>
	<xsl:variable name="photos_rel_dir">.</xsl:variable>
	
	<xsl:template match="item">
		<div align="center">
			<img border='0' class="dhx_folders_FTHUMBS_item_img">
				<xsl:attribute name="src"><xsl:value-of select="$thumbs_creator_url"/>?img=<xsl:value-of select="$photos_rel_dir"/><xsl:value-of select="./@name"/>&amp;width=94&amp;height=94</xsl:attribute>
			</img>
			<div class="dhx_folders_FTHUMBS_item_text">
				<span><xsl:value-of select="substring-before(./@name,'.')"/></span>
			</div>
		</div>
	</xsl:template>
	
</xsl:stylesheet>