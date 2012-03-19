require 'test_helper'

class SketchupControllerTest < ActionController::TestCase
  test "should get automate" do
    get :automate
    assert_response :success
  end

end
