package com.example.androidautodemo.templates.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.hardware.common.CarValue;
import androidx.car.app.hardware.info.EnergyProfile;
import androidx.car.app.hardware.info.Model;
import androidx.car.app.model.Action;
import androidx.car.app.model.CarIcon;
import androidx.car.app.model.Pane;
import androidx.car.app.model.PaneTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.lifecycle.Lifecycle;

import com.example.androidautodemo.R;

import java.util.concurrent.Executor;

public final class CarHardwareInfoScreen extends Screen {
    private static final String TAG = "showcase";

    boolean mHasModelPermission;
    boolean mHasEnergyProfilePermission;
    final Executor mCarHardwareExecutor;

    @Nullable
    EnergyProfile mEnergyProfile;

    public CarHardwareInfoScreen(@NonNull CarContext carContext) {
        super(carContext);
        mCarHardwareExecutor = ContextCompat.getMainExecutor(getCarContext());
        Lifecycle lifecycle = getLifecycle();
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        CarIcon ivTripStatus = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.status_default)).build();

        Pane.Builder paneBuilder = new Pane.Builder();
        if (allInfoAvailable()) {
            Row.Builder modelRowBuilder = new Row.Builder()
                    .setImage(ivTripStatus)
                    .setTitle(getCarContext().getString(R.string.model_info))
                    .addText(getCarContext().getString(R.string.no_model_permission));
            StringBuilder info = new StringBuilder();
            info.append(getCarContext().getString(R.string.manufacturer_unavailable));
            info.append(", ");
            info.append("ITCurves");
            info.append(", ");

            info.append("\n").append(getCarContext().getString(R.string.model_unavailable));
            info.append(", ");
            info.append("2008");
            info.append(",\n");

            info.append(getCarContext().getString(R.string.year_unavailable));
            info.append("2023");
            modelRowBuilder.addText(info);
            paneBuilder.addRow(modelRowBuilder.build());

            Row.Builder energyProfileRowBuilder = new Row.Builder()
                    .setTitle(getCarContext().getString(R.string.energy_profile));
            if (!mHasEnergyProfilePermission) {
                energyProfileRowBuilder.addText(getCarContext()
                        .getString(R.string.no_energy_profile_permission));
            } else {
                StringBuilder fuelInfo = new StringBuilder();
                if (mEnergyProfile.getFuelTypes().getStatus() != CarValue.STATUS_SUCCESS) {
                    fuelInfo.append(getCarContext().getString(R.string.fuel_types));
                    fuelInfo.append(": ");
                    fuelInfo.append(getCarContext().getString(R.string.unavailable));
                } else {
                    fuelInfo.append(getCarContext().getString(R.string.fuel_types));
                    fuelInfo.append(": ");
                    for (int fuelType : mEnergyProfile.getFuelTypes().getValue()) {
                        fuelInfo.append(fuelTypeAsString(fuelType));
                        fuelInfo.append(" ");
                    }
                }
                energyProfileRowBuilder.addText(fuelInfo);
                StringBuilder evInfo = new StringBuilder();
                if (mEnergyProfile.getEvConnectorTypes().getStatus() != CarValue.STATUS_SUCCESS) {
                    evInfo.append(" ");
                    evInfo.append(getCarContext().getString(R.string.ev_connector_types));
                    evInfo.append(": ");
                    evInfo.append(getCarContext().getString(R.string.unavailable));
                } else {
                    evInfo.append(getCarContext().getString(R.string.ev_connector_types));
                    evInfo.append(": ");
                    for (int connectorType : mEnergyProfile.getEvConnectorTypes().getValue()) {
                        evInfo.append(evConnectorAsString(connectorType));
                        evInfo.append(" ");
                    }
                }
                energyProfileRowBuilder.addText(evInfo);
            }
            paneBuilder.addRow(energyProfileRowBuilder.build());
        } else {
            paneBuilder.setLoading(true);
        }
        return new PaneTemplate.Builder(paneBuilder.build())
                .setHeaderAction(Action.BACK)
                .setTitle(getCarContext().getString(R.string.car_hardware_info))
                .build();
    }

    private boolean allInfoAvailable() {
        return true;
    }

    private String fuelTypeAsString(int fuelType) {
        switch (fuelType) {
            case EnergyProfile.FUEL_TYPE_UNLEADED:
                return "UNLEADED";
            case EnergyProfile.FUEL_TYPE_LEADED:
                return "LEADED";
            case EnergyProfile.FUEL_TYPE_DIESEL_1:
                return "DIESEL_1";
            case EnergyProfile.FUEL_TYPE_DIESEL_2:
                return "DIESEL_2";
            case EnergyProfile.FUEL_TYPE_BIODIESEL:
                return "BIODIESEL";
            case EnergyProfile.FUEL_TYPE_E85:
                return "E85";
            case EnergyProfile.FUEL_TYPE_LPG:
                return "LPG";
            case EnergyProfile.FUEL_TYPE_CNG:
                return "CNG";
            case EnergyProfile.FUEL_TYPE_LNG:
                return "LNG";
            case EnergyProfile.FUEL_TYPE_ELECTRIC:
                return "ELECTRIC";
            case EnergyProfile.FUEL_TYPE_HYDROGEN:
                return "HYDROGEN";
            case EnergyProfile.FUEL_TYPE_OTHER:
                return "OTHER";
            case EnergyProfile.FUEL_TYPE_UNKNOWN:
            default:
                return "UNKNOWN";
        }
    }

    private String evConnectorAsString(int evConnectorType) {
        switch (evConnectorType) {
            case EnergyProfile.EVCONNECTOR_TYPE_J1772:
                return "J1772";
            case EnergyProfile.EVCONNECTOR_TYPE_MENNEKES:
                return "MENNEKES";
            case EnergyProfile.EVCONNECTOR_TYPE_CHADEMO:
                return "CHADEMO";
            case EnergyProfile.EVCONNECTOR_TYPE_COMBO_1:
                return "COMBO_1";
            case EnergyProfile.EVCONNECTOR_TYPE_COMBO_2:
                return "COMBO_2";
            case EnergyProfile.EVCONNECTOR_TYPE_TESLA_ROADSTER:
                return "TESLA_ROADSTER";
            case EnergyProfile.EVCONNECTOR_TYPE_TESLA_HPWC:
                return "TESLA_HPWC";
            case EnergyProfile.EVCONNECTOR_TYPE_TESLA_SUPERCHARGER:
                return "TESLA_SUPERCHARGER";
            case EnergyProfile.EVCONNECTOR_TYPE_GBT:
                return "GBT";
            case EnergyProfile.EVCONNECTOR_TYPE_GBT_DC:
                return "GBT_DC";
            case EnergyProfile.EVCONNECTOR_TYPE_SCAME:
                return "SCAME";
            case EnergyProfile.EVCONNECTOR_TYPE_OTHER:
                return "OTHER";
            case EnergyProfile.EVCONNECTOR_TYPE_UNKNOWN:
            default:
                return "UNKNOWN";
        }
    }
}
