<%@ taglib uri="http://jakarta.apache.org/taglibs/request" prefix="req" %>
<%@ taglib uri="http://www.justgive.org/taglibs" prefix="jg" %>


<req:setattribute name="title">Enable Cookies</req:setattribute>
<req:setattribute name="metaDescription"></req:setattribute>
<req:setattribute name="metaKeywords"></req:setattribute>
<req:setattribute name="pageCode">C=NO COOKIE</req:setattribute>
<req:setattribute name="subheader">true</req:setattribute>

<req:setattribute name="content">
 
<h1>The browser you're using is rejecting cookies.</h1>  
<p>The Giving Basket requires a per session cookie that is temporarily  stored on your computer
until you close your browser, or your session is timed out (30 minutes of inactivity).
We are not storing any personal information with this cookie, in fact, it is just a token consisting of 32 letters and numbers (ex: '7A21630959DE2CF64221964DAAC3FB3C') used to uniquely identify your internet browser. 
This enables us to effectively, and securely track what you have placed in your giving basket, whether or not you are logged in, and so on.
</p>

<h3>Troubleshooting cookie issues.</h3>  
	<p>To make donations, you will need to set your browser to accept per session cookies. Below is a checklist of issues that may be causing you to be sent to this page.</p> 
<ol> 
	<li>Try clicking 'Back', and then click on the link that brought you here. 
	If your browser asks you to accept cookies, choose "accept" or "continue." </li>
	<li>Perhaps your browser is not set to allow cookies. <b>See below for instructions on how to accept cookies</b> for Internet Explorer or Netscape browsers. 
	For other browsers, click "Help" on your browser's taskbar to find out how to change this setting. </li>
	<li>Check your computer system's date and time. If they are set in the future, you may receive this error message. Change your computer system's date and time to the current date and time. </li>
	<li>Perhaps your browser is old and cannot support cookies. You can upgrade to a newer browser such as Internet Explorer or Netscape . </li>
	<li>Once your browser is is set to support cookies, try clicking 'Back', and then click on the link that brought you here. </li>
</ol> 

<h3>Enabling cookies in your browser.</h3>  
<p>To set your browser to accept your cookies, follow the directions below for the type of browser you have.  Then go back and click the link that brought you here again.</P>


<b>If you're using Internet Explorer 6.0</b><br>
At the top of your browser on your Task Bar:<br>
1. Choose <i>Tools</i>, then<br>
2. <i>Internet Options</i>.<br>
3. Click the Privacy tab,<br>
4. The default setting is medium, which will allow you to accept cookies.  
To change this setting, move the slider to determine which setting you prefer.  
If your setting has been set to "Custom," click on the <i>Advanced</i> button and choose 
<i>accept cookies</i>.


<p><b>If you're using Internet Explorer 5.0</b><br>
At the top of your browser on your Task Bar:<br>
1. Choose Tools, then<br>
2. <i>Internet Options</i>.<br>
3. Click the <i>Security</i> tab,<br>
4. Click <i>Internet</i>, then <i>Custom Level</i>.<br>
5. Scroll down to <i>Cookies</i> and choose <i>Enable</i>.


<p><b>If you're using Internet Explorer 4.0</b><br>
At the top of your browser on your Task Bar:<br>
1. Choose <i>View</i>, then<br>
2. <i>Internet Options</i>.<br>
3. Click the <i>Advanced</i> tab,<br>
4. Scroll down to the yellow exclamation icon under <i>Security</i> and choose the <i>enable cookies</i> option.<br> 


<p><b>If you're using Netscape Communicator 4.0</b><br>
At the top of you browser on your Task Bar, click:<br>
1. <i>Edit</i>, then<br>
2. <i>Preferences</i>, then<br>
3. Click on <i>Advanced</i>.<br>
4. Set your options in the box labeled "<i>Cookies</i>".


<p><b>If you're using Netscape Communicator 6.0</b><br>
At the top of you browser on your Task Bar, click:<br>
1. <i>Edit</i>, then<br>
2. <i>Preferences</i>, then<br>
3. Click on <i>Privacy</i> and <i>Security</i>.<br>
4. Then <i>Cookies</i>, and <br>
5. Select <i>Enable</i><br>
<br>

<p><b>If you're using Netscape 7.1/Mozilla 5.0</b> <br>
At the top of you browser on your Task Bar, click:<br>
1. Select <i>Preferences</i> from the Edit menu. <br>
2. Click on the arrow next to <i>Privacy & Security</i> in the scrolling window to expand. <br>
3. Under <i>Privacy & Security</i>, select <i>Cookies.</i> <br>
4. Select <i>Enable all cookies</i>. <br>
5. Click <i>OK</i>. <br>

<p><b>If you're using Netscape Communicator 7.2</b><br>
At the top of you browser on your Task Bar, click:<br>
1. <i>Tools</i>, then<br>
2. Select <i>Cookie Manager</i>, then<br>
3. Click on <i>Allow Session Cookies from this Site</i>

<br>
<p><b>If you're using Mozilla Firefox 1.0</b><br>
At the top of your browser on your Task Bar:<br>
1. Choose Tools, then<br>
2. Click on <i>Options...</i>,<br>
3. Click the <i>Privacy</i> icon in the left panel,<br>
4. Click the <i>Cookies</i> plus sign to open a new panel,<br>
5. Scroll down and check <i>Allow sites to set cookies</i>.<br>
For additional security you may check <i>for the originating web site only</i>,<br>
and you may also select <i>Keep Cookies: until I close Firefox</i> to ensure<br>
that cookies are not permanently stored on your computer.<br>

<br>
If you need assisstance please email our <a href="mailto:support@justgive.org">support staff</a>.<br>
<p class="fineprint">(Instructions borrowed from eBay and Google)</p>
</req:setattribute>

<%-- this sets the section for the page which controls the header graphic, nav and third column. This can be overrided in the URL request --%>
<req:setattribute name="section">about-us</req:setattribute>

<%-- use the following template for display --%>
<jsp:forward page="/templates/template_noCol.jsp" />

