package org.justgive.models.propertyEditor;

import org.justgive.models.User;
import org.justgive.services.UserManager;

import java.beans.PropertyEditorSupport;

public class UserPropertyEditor extends PropertyEditorSupport {
    private final UserManager userManager;

    public UserPropertyEditor(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(userManager.findOne(Long.getLong(text)));
    }

    @Override
    public String getAsText() {
        Object value = getValue();

        if (value instanceof User) {
            return String.valueOf(((User) value).getId());
        } else {
            return super.getAsText();
        }
    }
}
