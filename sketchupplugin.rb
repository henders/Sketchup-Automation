require 'sketchup.rb'

def DrawCube
    width, height, depth = [6.feet, 5.feet, 4.feet]
    
    model = Sketchup.active_model
    model.start_operation $exStrings.GetString("Create Box")
    
    entities = model.active_entities

    # If you wanted the box to be created as simple top level entities
    # rather than a Group, you could comment out the following two lines.
    group = entities.add_group
    entities = group.entities
    
    # First we will create a rectangle for the base.  There are a few
    # variations on the add_face method.  This uses the version that
    # takes points and automatically creates the edges needed.
    pts = []
    pts[0] = [0, 0, 0]
    pts[1] = [width, 0, 0]
    pts[2] = [width, depth, 0]
    pts[3] = [0, depth, 0]
    base = entities.add_face pts
    
    # You could use a similar technique to crete the other faces of
    # the box.  For this example, we will use the pushpull method instead.
    # When you use pushpull, the direction is determined by the direction
    # of the fromnt of the face.  In order to control the direction and
    # get the pushpull to go in the direction we want, we first check the
    # direction of the face normal.  If it is not in the direction that
    # we want, we will reverse the sign of the distance.
    height = -height if( base.normal.dot(Z_AXIS) < 0 )
    
    # Now we can do the pushpull
    base.pushpull height
    
    # Now we are done and we can end the operation
    model.commit_operation
end

# Create the WebDialog instance
my_dialog = UI::WebDialog.new(dialog_title="Automation Viewport", scrollable=false, pref_key="AutomationPlugin", width=200, height=300)

# Attach an action callback
my_dialog.add_action_callback("implement") do |web_dialog, params|
    UI.messagebox("You called ruby_messagebox with: " + params.to_s)
end

# Show our rails app index page
my_dialog.set_url("http://localhost:3000/index.html")
my_dialog.show()


#-----------------------------------------------------------------------------
file_loaded("test.rb")



