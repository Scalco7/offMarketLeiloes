package com.backend.offMarketLeiloes.domain.entities;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.backend.offMarketLeiloes.domain.enums.EPropertyStatus;
import com.backend.offMarketLeiloes.domain.enums.EPropertyType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "property")
public class Property {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(updatable = true, nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double valuedPrice;

    @Column(nullable = false)
    private Double currentPrice;

    @Column(updatable = true, nullable = false)
    private LocalDateTime auctionDateTime;

    @Column(nullable = false)
    private String auctioneerName;

    @Column(nullable = false)
    private String auctionLink;

    @Column(nullable = true)
    private String imageLink;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EPropertyType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EPropertyStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private PropertyAddress address;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FavoriteProperty> favoriteProperties = new ArrayList<>();
}
