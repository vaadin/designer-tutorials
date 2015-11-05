Vaadin Designer demo: email client
==================================
If you just want to see the end result, can fork this project and run the code yourself. However, I recommend starting from scratch and going through all the steps yourself.

In the following tutorial you will build a bit more extensive application UI than in some of the earlier tutorials. This tutorial familiarizes you with the different features of [Vaadin Designer](https://vaadin.com/designer) and gives you ideas of how the tool can be used more effectively. The tutorial is based on a video, where Marc and Ville from Vaadin demonstrate how Vaadin Designer can be used to create a substantial application from an existing design.

[![Vaadin Designer demonstration](https://cloud.githubusercontent.com/assets/1398470/10881298/3b1b49ba-816a-11e5-812f-873f4ae5e953.png)](https://www.youtube.com/watch?v=-6Ix8WHgD6g "Vaadin Designer demonstration")

In the video they use a design from a site called www.100daysui.com where Paul Flavius Nechita made a UI design per day. The design for day [#38](https://dribbble.com/shots/2181203-Day-038-Email-Client/attachments/402034) was a mockup of an email client. This tutorial breaks down the steps in the video to help you follow the same workflow. Let’s see how close we can get to the design with only a little effort.

Start by creating a project
---------------------------
Create a new Vaadin 7 project either by using the Vaadin Eclipse plugin or Maven. You should use version 7.5.7 or newer of Vaadin. If you are using Maven, just use vaadin-archetype-application.
```
mvn archetype:generate -DarchetypeGroupId=com.vaadin  -DarchetypeArtifactId=vaadin-archetype-application -DarchetypeVersion=7.5.8
```
Getting a head start
--------------------
The application UI is divided into three areas: the menu layout on the left side, the action toolbar on the top right, and the list of email messages in the content area on the right. To get a good head start you can use a template called Responsive Application. The template has a menu for the left side and a content area.

The application layout created with the template is also responsive, meaning that menu items are displayed in a more compact way when the width of the browser window gets smaller, e.g. in a mobile browser. The responsiveness is done using Vaadin Framework’s responsive extension and specific style names in Valo theme. The responsive part is out of the scope of this tutorial.

* Create a new design in your project by right clicking your project and selecting __New › Other… › Vaadin Design.__ 
* In the dialog, select the __Responsive Application template__ and give the design the name ApplicationDesign.

__Your design from the template should look like this__
![Starting point from responsive template](https://cloud.githubusercontent.com/assets/1398470/10730425/86d938de-7bf8-11e5-8650-7a841071718d.png)

Beautiful buttons
-----------------
* Remove the blue menuTitle HorizontalLayout that has the text ‘The Application’.
* Select the top most button userButton and change its style from the properties panel.
  * Clear the value of PrimaryStyleName property and set the StyleName property value with opening the style editor ![property editor button](https://cloud.githubusercontent.com/assets/1398470/10730428/8b182eaa-7bf8-11e5-9735-859c9d878d1d.png). 
  * From ValoTheme, add styles `BUTTON_BORDERLESS` and `BUTTON_ICON_ALIGN_TOP`. 
  * Click OK to close the style editor.

As you can see, the button style changed and the button StyleName property has the value ‘borderless icon-align-top’. Sometimes it’s faster just to write the style name instead of using the style editor, if you remember the style name, that is.

* Modify the userButton
  * Set the height to 150px.
  * Change its caption to Compose.
  * Change its name to composeButton. 
  * Give a new icon with the icon editor. Select the Font Icons tab and select the icon called `EDIT`.

The inbox button is ok and can be moved below the composeButton. The rest of the buttons you need to change: name, caption, and icon properties. The template doesn’t contain enough buttons, so you need to add a couple. The easiest way is to copy one of the buttons and paste it a couple of times. To get the little number badge by the button caption, you need to set the HtmlContentAllowed checkbox and set the button caption to contain an Html snippet e.g. `<span class=”valo-menu-badge”>99+</span>`.

![Icon FILES_O](https://cloud.githubusercontent.com/assets/1398470/10730429/8ea45c56-7bf8-11e5-81a2-2a975f14fef2.png) FILES_O, ![Icon PAPER_PLANE](https://cloud.githubusercontent.com/assets/1398470/10730431/911a5b52-7bf8-11e5-824a-f3d58cd72e9d.png) PAPER_PLANE, ![Icon EXCLAMATION](https://cloud.githubusercontent.com/assets/1398470/10730434/92742bcc-7bf8-11e5-9506-09ac57da1d29.png) EXCLAMATION, ![Icon TRASH_O](https://cloud.githubusercontent.com/assets/1398470/10730438/95749ac8-7bf8-11e5-9583-cbc404beca5c.png) TRASH_O,  ![Icon FLAG](https://cloud.githubusercontent.com/assets/1398470/10730439/976f3a86-7bf8-11e5-82ee-1dd179ffaefa.png) FLAG.

__At this point your application should look like this__  
![Buttons made a bit better](https://cloud.githubusercontent.com/assets/1398470/10730445/9fc418dc-7bf8-11e5-9127-9d325c71a149.png)

The left side menu looks quite nice already, but you need to style it up a bit to get it to look more like the UI mockup. You need to add the styles to your theme scss file. Remember that any Valo variables need to be set before the valo.scss is imported. All the other style definitions go inside your theme mixin.

* Change the color of the menu background: `$valo-menu-background-color: #433;`
* Change the menu badge roundness: `$v-border-radius: 10px;`

Make the menu wider when the layout is in the “desktop” mode. Add a responsive extension CSS “breakpoint”. The topic of responsive layouts is out of our scope, but if you are interested, check out the Book of Vaadin chapter about it: https://vaadin.com/book/-/page/themes.responsive.html.
```css
.valo-menu-responsive[width-range~="1101px-"] {
    .valo-menu-item-caption {
        min-width: 150px;
    }
}
```
Add some space around the menu items by making the menu item line height higher and then realign the badge.
```css
.valo-menu-item {
    line-height: 45px;
    .valo-menu-badge {
        top:  12px;
    }
}
```
And finally make the compose button more dominant. Add another StyleName called compose to composeButton. With these Sass (and CSS) directives, your buttons should look correct.

```css
.compose {
  padding-top: 50px;
  .v-icon {
    	font-size: 50px;
  	color: #ccc;
    	padding-bottom: 15px;
  }
  
  // Make the compose button react when mousing over
  &:hover {
      color: white;
      .v-icon {
          color: white;
      }
  }
  
  // Workaround for always visible scroll bar
  &:after {
      right: 0px;
  }
}
```  
__With correctly styled buttons your application should look like this__  
![Beautiful buttons](https://cloud.githubusercontent.com/assets/1398470/10730446/a2f026f4-7bf8-11e5-9484-789606745fbd.png)

Toolbar
-------
Now that the left side navigation is looking good, let’s implement the toolbar above the message list.
* First make the content called VerticalLayout full size and then drag a HorizontalLayout and a Panel into the content layout. 
  * Make sure the Panel is full size and has ExpandRatio 1.0, and the HorizontalLayout has 100% width and an undefined height.
  * The expand is set automatically when you click the quick actions for the layout sizes in the Designer. 
* Add spacing and margins to the HorizontalLayout and remove margins and spacing from the content layout. 
* Set the `PANEL_BORDERLESS` style for the Panel.

Build the toolbar component structure
* Drag a Label into the toolbar HorizontalLayout 
  * Value: Sort by
  * Align it middle-left with the quick actions
* Drag a ComboBox into the toolbar layout
  * Set a width of 150px
* Drag buttons, give them a name, remove their captions, and add their icons: 
  * Delete button icon: `TIMES`
  * Reply button icon: `MAIL_REPLY`
  * Reply all button icon: `MAIL_REPLY_ALL`
  * Forward button icon: `MAIL_FORWARD.`
* Wrap reply button with a CssLayout by right clicking the button in the Outline and select Wrap with… 
* Move ‘reply all’ and ‘forward’ buttons to the CssLayout and change the layout StyleName to `LAYOUT_COMPONENT_GROUP` from Valo Theme.

Message list
------------
The scrollable Panel will contain the list of messages in each of the selected folders. There’s a slight challenge to design the message look-and-feel, because the content in the Panel is dynamic and the 1.0 version of Vaadin Designer doesn’t support nested designs nor component compositions. This is likely to be changed in the future, but in the meanwhile, what you want to do in case of dynamic content is to first design the composition in the place it’s going to appear and then detach it to a separate design file. This way you can benefit both from having the visual tools for designing and still keep the composition accessible from Java. Drag a VerticalLayout into the content Panel. Set its width to 100% and put the spacing on. Now you can start with the message itself.

Build the message component structure
*  Drag a HorizontalLayout into the VerticalLayout inside content Panel.
  *  Width 100%, and height 125px.
  *  StyleName: message-layout
*  Drag a Button into the HorizontalLayout
  *  Caption: n/a
  *  Name: indicatorButton
  *  Icon: Font icon `STAR`
  *  StyleName: borderless
*  Drag a VerticalLayout into the HorizontalLayout
  *  Expand the width and height to 100%
  *  StyleName: content
*  Drag a Label into the VerticalLayout 
  *  Name: senderLabel
  *  Expand it to 100% width
  *  Set the label value to “This is the subject”
  *  StyleName: bold and message-sender
*  Drag another Label into the VerticalLayout below the senderLabel
  *  Name: messageLabel
  *  Expand the label to 100% width and height
  *  Label caption: This is the title
  *  Label value: Put something e.g. lorem ipsum
  *  StyleName: message

Provided you added the style names to the components as instructed, you can use these style definitions.

To get the horizontal line under each message:
```css
.header, .content {
    border-bottom: 1px solid #ccc;
}
```
To get the message title and content to behave even if they don’t fit in the given space:
```css
.message-layout {
    .message-sender {
        white-space: nowrap;
        overflow: hidden; 
    }
   
    .message {
        overflow: hidden;
    }
}
```
Finally enable the right side margin of the message content VerticalLayout from the properties. Individual margins can be enabled using the margin editor, just click ![property editor button](https://cloud.githubusercontent.com/assets/1398470/10730428/8b182eaa-7bf8-11e5-9735-859c9d878d1d.png).

__Completed message item design__  
![Message design ready](https://cloud.githubusercontent.com/assets/1398470/10730449/aa87145e-7bf8-11e5-9b53-b128a4615c8f.png)

As the final step in implementing the message component you need to create a new design. Name it MessageDesign and use the blank template. Then copy-paste the whole message layout to your new design. At the time of writing this, there is a limitation in Designer, which prevents you pasting to a blank design in the visual view. You need to switch to the source mode and paste your content in between the body elements.

Create a new Java class called Message as a subclass of MessageDesign. This Message class is the place where you would implement the component logic, but for now, you can leave it empty. Create a bunch of Message instances in a loop to demonstrate how the dynamic content works in your application design
```java
@Override
protected void init(VaadinRequest vaadinRequest) {
    final ApplicationDesign design = new ApplicationDesign();
    setContent(design);

    for (int i = 0; i < 10; i++) {
        design.messageList.addComponent(new Message());
    }
}
```

Final result
------------
The final result looks quite nice. You started from a basic responsive application template and took some simple steps to modify the design to look like the design mockup. First you modified the left side menu to have the correct buttons and styled the compose button to be more dominant. Then you implemented the rest of the application layout to contain a toolbar and a scrollable message list. Finally you created a message component to be used dynamically in your Java code. After these steps your design should look more or less like in the image below. Great job!

__Finished application__<br/>
![Final result](https://cloud.githubusercontent.com/assets/1398470/10730450/ad4dae46-7bf8-11e5-9dd2-a00dda94b1cd.png)

What now?
---------
As you probably noticed, the end result isn't completely identical with the original mockup. The email dates are not there and the menu has some more info at the bottom. These are left for you to work on and now you can try out your new Vaadin Designer skills for real!
