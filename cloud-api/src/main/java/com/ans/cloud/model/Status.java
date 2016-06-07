package com.ans.cloud.model;

import java.io.Serializable;

/**
 * Created by anzhen on 2015/12/29.
 */
public enum Status implements Serializable {
    /**
     * The instance is active
     */
    ACTIVE,
    /**
     * The instance has not finished the original build process
     */
    BUILD,
    /**
     * The instance is currently being rebuilt
     */
    REBUILD,
    /**
     * The instance is suspended, either by request or necessity. This status appears for only the following
     * hypervisors:
     * Xeninstance/XCP, KVM, and ESXi. Administrative users may suspend an instance if it is infrequently used or to
     * perform system maintenance. When you suspend an instance, its VM state is stored on disk, all memory is
     * written to disk,
     * and the virtual machine is stopped. Suspending an instance is similar to placing a device in hibernation;
     * memory and vCPUs become available to create other instances.
     */
    SUSPENDED,
    /**
     * In a paused state, the state of the instance is stored in RAM. A paused instance continues to run in frozen
     * state.
     */
    PAUSED,
    /**
     * instance is performing the differential copy of data that changed during its initial copy. instance is down
     * for this stage.
     */
    RESIZE,
    /**
     * System is awaiting confirmation that the instance is operational after a move or resize.
     */
    VERIFY_RESIZE,
    /**
     * The resize or migration of a instance failed for some reason. The destination instance is being cleaned up and
     * the original source instance is restarting.
     */
    REVERT_RESIZE,
    /**
     * The password is being reset on the instance.
     */
    PASSWORD,
    /**
     * The instance is in a soft reboot state. A reboot command was passed to the operating system.
     */
    REBOOT,
    /**
     * The instance is hard rebooting. This is equivalent to pulling the power plug on a physical instance,
     * plugging it back in, and rebooting it.
     */
    HARD_REBOOT,
    /**
     * The instance is permanently deleted.
     */
    DELETED,
    /**
     * The state of the instance is unknown. Contact your cloud provider.
     */
    UNKNOWN,
    /**
     * The instance is in error.
     */
    ERROR,
    /**
     * The instance is powered off and the disk image still persists.
     */
    STOPPED,
    /**
     * The virtual machine (VM) was powered down by the user, but not through the OpenStack Compute API.
     */
    SHUTOFF,
    /**
     * OpenStack4j could not find a Status mapping for the current reported Status.  File an issue indicating the
     * missing state
     */
    UNRECOGNIZED;

    public static Status forValue(String value) {
        if (value != null) {
            for (Status s : Status.values()) {
                if (s.name().equalsIgnoreCase(value))
                    return s;
            }
        }
        return Status.UNRECOGNIZED;
    }
}
