package org.justgive.action;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

class ActionLoader {
    private static Logger jgLog = LoggerFactory.getLogger(ActionLoader.class);

    private Class<? extends Action> classType;

    ActionLoader(Class<? extends Action> classType) {
        if (classType == null) throw new IllegalArgumentException("Null classType");
        this.classType = classType;
    }

    Action loadAction() {
        try {
            Action action = classType.newInstance();
            jgLog.debug("Instantiated Action: " + action.getClass().getName());
            return action;
        } catch (Exception e) {
            jgLog.info(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
