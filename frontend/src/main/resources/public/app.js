Ext.application({
    name: 'Sencha',
    requires: ['Ext.tab.Panel','Ext.dataview.NestedList','Ext.TitleBar', 'Ext.form.Panel', 'Ext.form.FieldSet', 'Ext.field.Number', 'Ext.field.TextArea'],
    launch: function() {
        Ext.create('Ext.tab.Panel', {

            fullscreen: true,
            tabBarPosition: 'bottom',

            items: [
                {
                    id: 'contactlist',
                    xtype: 'nestedlist',
                    title: 'Phonebook',
                    iconCls: 'bookmarks',
                    displayField: 'contact',

                    store:
                    {
                        type: 'tree',

                        fields: [
                            'id', 'contact','info',
                            {name: 'leaf', defaultValue: true}
                        ],

                        root: {
                            leaf: false
                        },

                        proxy: {
                            type: 'ajax',
                            url: 'contactList',
                            reader: {
                                type: 'json',
                                rootProperty: 'contacts'
                            }
                        }
                    },

                    detailCard: {
                        xtype: 'formpanel',
                        scrollable: true,
                        styleHtmlContent: true,

                        listeners: {
                            submit: function(form, result) {
                                this.up('nestedlist').getStore().load();
                                // Switch to main tab.
                                this.up('nestedlist').animateActiveItem(0, {type: 'slide', direction: 'right'});
                                this.up('nestedlist').syncToolbar();
                            }
                        },

                        items: [
                            {
                                xtype: 'button',
                                text: 'Update',
                                ui: 'confirm',
                                handler: function() {
                                    this.up('formpanel').setUrl('updateContact');
                                    this.up('formpanel').submit();
                                }
                            },
                            {
                                id: 'contactfields',
                                xtype: 'fieldset',
                                title: 'Add a new contact',

                                items: [
                                    {
                                        name: 'cid',
                                        xtype: 'numberfield',
                                        hidden: true
                                    },
                                    {
                                        name: 'contact',
                                        xtype: 'textfield',
                                        label: 'Name'
                                    },
                                    {   
                                        name: 'info',
                                        xtype: 'textareafield',
                                        label: 'Information'
                                    }
                                ]
                            },
                            {
                                xtype: 'button',
                                text: 'Delete',
                                ui: 'decline',
                                handler: function() {
                                    this.up('formpanel').setUrl('deleteContact');
                                    this.up('formpanel').submit();
                                }
                            }    
                        ]  
                    },
                    
                    listeners: {
                        itemtap: function(nestedList, list, index, element, post) {
                            this.getDetailCard().setValues({
                                cid: post.get('id'),
                                contact: post.get('contact'),
                                info: post.get('info')
                            });
                        }
                    }

                }, {
                    title: 'New Contact',
                    iconCls: 'user',
                    xtype: 'formpanel',
                    url: 'addContact',
                    layout: 'vbox',

                    items: [
                        {
                            xtype: 'fieldset',
                            title: 'Add a new contact',
                            items: [
                                {
                                    name: 'contact',
                                    xtype: 'textfield',
                                    label: 'Name'
                                },
                                {
                                    name: 'info',
                                    xtype: 'textareafield',
                                    label: 'Information'
                                }
                            ]
                        },
                        {
                            xtype: 'button',
                            text: 'Add Contact',
                            ui: 'confirm',
                            handler: function() {
                                this.up('formpanel').submit();
                            }
                        }
                    ],

                    listeners: {
                        submit: function(form, result) {
                            this.up('tabpanel').getComponent('contactlist').getStore().load();
                            // Switch to main tab
                            this.up('tabpanel').animateActiveItem(0, {type: 'slide', direction: 'right'});
                            this.reset();
                        }
                    }
                }
            ]
        });
    }
});